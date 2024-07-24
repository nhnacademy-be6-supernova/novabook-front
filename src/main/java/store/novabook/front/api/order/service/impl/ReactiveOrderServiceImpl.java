package store.novabook.front.api.order.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import store.novabook.front.api.coupon.dto.request.GetCouponAllRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressResponse;
import store.novabook.front.api.member.coupon.dto.GetCouponIdsResponse;
import store.novabook.front.api.order.WebClientService;
import store.novabook.front.api.order.dto.response.GetWrappingPaperAllResponse;
import store.novabook.front.api.order.service.ReactiveOrderService;
import store.novabook.front.api.point.dto.GetMemberPointResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.order.dto.OrderViewDTO;

@RequiredArgsConstructor
@Service
public class ReactiveOrderServiceImpl implements ReactiveOrderService {

	private final WebClientService webClientService;

	/**
	 * 주문 페이지 정보를 가져오는 로직
	 *
	 * @param bookDTOS 책id 수량 포함
	 * @param memberId 회원 식별번호
	 * @return 주문 페이지에 선택지를 보여주기 위한 값들
	 */
	@Override
	public Mono<OrderViewDTO> getOrder(List<BookDTO> bookDTOS, Long memberId) {
		Set<Long> bookIds = extractBookIds(bookDTOS);
		boolean isPackage = bookDTOS.stream().anyMatch(BookDTO::isPackage);

		Mono<Set<Long>> categoryFuture = webClientService.fetchCategoryIdsAsync(bookIds);
		Mono<GetWrappingPaperAllResponse> papersFuture = webClientService.getWrappingPaperAllList()
			.map(ApiResponse::getBody);
		Mono<GetDeliveryFeeResponse> deliveryFeeFuture = webClientService.getRecentDeliveryFee()
			.map(ApiResponse::getBody);

		List<String> dates = generateNextSixDays();

		if (memberId != null) {
			return getOrderForMember(categoryFuture, papersFuture, deliveryFeeFuture, dates, bookIds, isPackage);
		}
		return getOrderForGuest(papersFuture, deliveryFeeFuture, dates, isPackage);
	}

	private Set<Long> extractBookIds(List<BookDTO> bookDTOS) {
		return bookDTOS.stream().map(BookDTO::id).collect(Collectors.toUnmodifiableSet());
	}

	private List<String> generateNextSixDays() {
		return IntStream.range(0, 6)
			.mapToObj(i -> LocalDate.now().plusDays(i).format(DateTimeFormatter.ofPattern("M/d (E)")))
			.toList();
	}

	private Mono<OrderViewDTO> getOrderForMember(Mono<Set<Long>> categoryFuture,
		Mono<GetWrappingPaperAllResponse> papersFuture, Mono<GetDeliveryFeeResponse> deliveryFeeFuture,
		List<String> dates, Set<Long> bookIds, boolean isPackage) {

		return Mono.zip(categoryFuture, papersFuture, deliveryFeeFuture,
			webClientService.fetchCouponIdsAsync().map(ApiResponse::getBody),
			webClientService.getMemberPointTotal().map(ApiResponse::getBody),
			webClientService.getMemberAddressAll().map(ApiResponse::getBody)).flatMap(tuple -> {
			Set<Long> categoryIds = tuple.getT1();
			GetWrappingPaperAllResponse papers = tuple.getT2();
			GetDeliveryFeeResponse deliveryFeeInfo = tuple.getT3();
			GetCouponIdsResponse couponIds = tuple.getT4();
			GetMemberPointResponse myPointResponse = tuple.getT5();
			long myPoint = myPointResponse.pointAmount();
			List<GetMemberAddressResponse> memberAddresses = tuple.getT6().memberAddresses();

			GetCouponAllRequest couponRequest = GetCouponAllRequest.builder()
				.couponIdList(couponIds.couponIds())
				.categoryIdList(categoryIds)
				.bookIdList(bookIds)
				.build();

			Mono<ApiResponse<GetCouponAllResponse>> couponsFuture = webClientService.getSufficientCouponAll(
				couponRequest);

			return couponsFuture.map(response -> OrderViewDTO.builder()
				.isPackable(isPackage)
				.coupons(response.getBody().couponResponseList())
				.wrappingPapers(papers.getWrappingPaperResponse())
				.memberAddresses(memberAddresses)
				.dates(dates)
				.myPoint(myPoint)
				.deliveryFeeInfo(deliveryFeeInfo)
				.build());
		});
	}

	private Mono<OrderViewDTO> getOrderForGuest(Mono<GetWrappingPaperAllResponse> papersFuture,
		Mono<GetDeliveryFeeResponse> deliveryFeeFuture, List<String> dates, boolean isPackage) {

		return Mono.zip(papersFuture, deliveryFeeFuture)
			.map(tuple -> OrderViewDTO.builder()
				.isPackable(isPackage)
				.wrappingPapers(tuple.getT1().getWrappingPaperResponse())
				.dates(dates)
				.deliveryFeeInfo(tuple.getT2())
				.build());
	}

}

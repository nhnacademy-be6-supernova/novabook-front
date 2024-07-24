package store.novabook.front.api.order.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import store.novabook.front.api.category.dto.response.GetCategoryIdsByBookIdResponse;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.api.coupon.dto.request.GetCouponAllRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressListResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressResponse;
import store.novabook.front.api.member.coupon.service.MemberCouponClient;
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
	private final CategoryClient categoryClient;
	private final MemberCouponClient memberCouponClient;

	@Override
	public Mono<OrderViewDTO> getOrder(List<BookDTO> bookDTOS, Long memberId) {
		Set<Long> bookIds = extractBookIds(bookDTOS);
		boolean isPackage = bookDTOS.stream().anyMatch(BookDTO::isPackage);

		Mono<GetWrappingPaperAllResponse> papersFuture = webClientService.getWrappingPaperAllList()
			.map(ApiResponse::getBody);
		Mono<GetDeliveryFeeResponse> deliveryFeeFuture = webClientService.getRecentDeliveryFee()
			.map(ApiResponse::getBody);

		List<String> dates = generateNextSixDays();

		Set<Long> categoryIdList = new HashSet<>();
		bookIds.forEach(bookId -> {
			ApiResponse<GetCategoryIdsByBookIdResponse> response = categoryClient.getCategoryByBId(bookId);
			if (response.getBody() == null) {
				throw new NullPointerException("CategoryResponse body is null for bookId: " + bookId);
			}
			List<Long> categoryIds = response.getBody().categoryIds();
			categoryIdList.addAll(categoryIds);
		});

		if (memberId != null) {
			return getOrderForMember(categoryIdList, papersFuture, deliveryFeeFuture, dates, bookIds, isPackage);
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

	private Mono<OrderViewDTO> getOrderForMember(Set<Long> categoryIdList,
		Mono<GetWrappingPaperAllResponse> papersFuture, Mono<GetDeliveryFeeResponse> deliveryFeeFuture,
		List<String> dates, Set<Long> bookIds, boolean isPackage) {

		List<Long> couponIdList = memberCouponClient.getMemberCoupon().getBody().couponIds();
		GetCouponAllRequest couponRequest = GetCouponAllRequest.builder()
			.couponIdList(couponIdList)
			.categoryIdList(categoryIdList)
			.bookIdList(bookIds)
			.build();

		Mono<GetCouponAllResponse> couponFuture = webClientService.getSufficientCouponAll(couponRequest)
			.map(ApiResponse::getBody);

		Mono<GetMemberPointResponse> memberPointFuture = webClientService.getMemberPointTotal()
			.map(ApiResponse::getBody);

		Mono<GetMemberAddressListResponse> memberAddressFuture = webClientService.getMemberAddressAll()
			.map(ApiResponse::getBody);

		return Mono.zip(couponFuture, papersFuture, deliveryFeeFuture, memberPointFuture, memberAddressFuture)
			.map(tuple -> {
				GetCouponAllResponse couponResponse = tuple.getT1();
				GetWrappingPaperAllResponse papers = tuple.getT2();
				GetDeliveryFeeResponse deliveryFeeInfo = tuple.getT3();
				GetMemberPointResponse myPointResponse = tuple.getT4();
				List<GetMemberAddressResponse> memberAddresses = tuple.getT5().memberAddresses();

				return OrderViewDTO.builder()
					.isPackable(isPackage)
					.coupons(couponResponse.couponResponseList())
					.wrappingPapers(papers.getWrappingPaperResponse())
					.memberAddresses(memberAddresses)
					.dates(dates)
					.myPoint(myPointResponse.pointAmount())
					.deliveryFeeInfo(deliveryFeeInfo)
					.build();
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

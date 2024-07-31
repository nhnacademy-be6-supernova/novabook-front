package store.novabook.front.api.order.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressListResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressResponse;
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

	@Override
	public Mono<OrderViewDTO> getOrder(List<BookDTO> bookDTOS, Long memberId) {
		boolean isPackage = bookDTOS.stream().anyMatch(BookDTO::isPackage);

		Mono<GetWrappingPaperAllResponse> papersFuture = webClientService.getWrappingPaperAllList()
			.map(ApiResponse::getBody);
		Mono<GetDeliveryFeeResponse> deliveryFeeFuture = webClientService.getRecentDeliveryFee()
			.map(ApiResponse::getBody);

		List<String> dates = generateNextSixDays();

		if (memberId != null) {
			return getOrderForMember(papersFuture, deliveryFeeFuture, dates, isPackage);
		}
		return getOrderForGuest(papersFuture, deliveryFeeFuture, dates, isPackage);
	}

	private Mono<OrderViewDTO> getOrderForMember(Mono<GetWrappingPaperAllResponse> papersFuture,
		Mono<GetDeliveryFeeResponse> deliveryFeeFuture, List<String> dates, boolean isPackage) {
		Mono<GetMemberPointResponse> memberPointFuture = webClientService.getMemberPointTotal()
			.map(ApiResponse::getBody);
		Mono<GetMemberAddressListResponse> memberAddressFuture = webClientService.getMemberAddressAll()
			.map(ApiResponse::getBody);

		return Mono.zip(papersFuture, deliveryFeeFuture, memberPointFuture, memberAddressFuture).map(tuple -> {
			GetWrappingPaperAllResponse papers = tuple.getT1();
			GetDeliveryFeeResponse deliveryFeeInfo = tuple.getT2();
			GetMemberPointResponse myPointResponse = tuple.getT3();
			List<GetMemberAddressResponse> memberAddresses = tuple.getT4().memberAddresses();

			return OrderViewDTO.builder()
				.isPackable(isPackage)
				.wrappingPapers(papers.getWrappingPaperResponse())
				.memberAddresses(memberAddresses)
				.dates(dates)
				.myPoint(myPointResponse.pointAmount())
				.deliveryFeeInfo(deliveryFeeInfo)
				.build();
		});
	}

	private List<String> generateNextSixDays() {
		return IntStream.range(0, 6)
			.mapToObj(i -> LocalDate.now().plusDays(i).format(DateTimeFormatter.ofPattern("M/d (E)")))
			.toList();
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

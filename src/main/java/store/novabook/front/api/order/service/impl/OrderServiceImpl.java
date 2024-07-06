package store.novabook.front.api.order.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.api.coupon.client.CouponClient;
import store.novabook.front.api.coupon.dto.request.GetCouponAllRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponResponse;
import store.novabook.front.api.delivery.client.DeliveryFeeClient;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressResponse;
import store.novabook.front.api.member.address.service.MemberAddressClient;
import store.novabook.front.api.member.coupon.service.MemberCouponClient;
import store.novabook.front.api.order.client.WrappingPaperClient;
import store.novabook.front.api.order.dto.request.PaymentRequest;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;
import store.novabook.front.api.order.service.OrderService;
import store.novabook.front.api.order.service.OrdersSagaClient;
import store.novabook.front.api.point.dto.request.GetPointHistoryRequest;
import store.novabook.front.api.point.dto.response.GetPointHistoryResponse;
import store.novabook.front.api.point.service.PointHistoryClient;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.order.dto.OrderViewDTO;
import store.novabook.front.store.order.repository.RedisOrderRepository;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

	private final WrappingPaperClient wrappingPaperClient;
	private final CouponClient couponClient;
	private final CategoryClient categoryClient;
	private final MemberCouponClient memberCouponClient;
	private final PointHistoryClient pointHistoryClient;
	private final MemberAddressClient memberAddressClient;
	private final DeliveryFeeClient deliveryFeeClient;
	private final OrdersSagaClient ordersSagaClient;
	private final RedisOrderRepository redisOrderRepository;

	@Override
	@Transactional(readOnly = true)
	public OrderViewDTO getOrder(List<BookDTO> bookDTOS, Long memberId) {
		Set<Long> bookIds = new HashSet<>();
		boolean isPackage = false;

		for (BookDTO bookDTO : bookDTOS) {
			bookIds.add(bookDTO.id());
			if (bookDTO.isPackage()) {
				isPackage = true;
			}
		}

		Set<Long> categoryIdList = new HashSet<>();
		bookIds.forEach(bookId -> {
			List<Long> categoryIds = categoryClient.getCategoryByBId(bookId).getBody().categoryIds();
			categoryIdList.addAll(categoryIds);
		});

		List<Long> couponIdList = memberCouponClient.getMemberCoupon().getBody().couponIds();

		List<GetWrappingPaperResponse> papers = wrappingPaperClient.getWrappingPaperAllList().getBody().getWrappingPaperResponse();

		GetCouponAllRequest couponRequest = GetCouponAllRequest.builder()
			.couponIdList(couponIdList)
			.categoryIdList(categoryIdList)
			.bookIdList(bookIds)
			.build();

		List<GetCouponResponse> coupons = couponClient.getSufficientCouponAll(couponRequest)
			.getBody()
			.couponResponseList();

		long myPoint = pointHistoryClient.getPointHistoryListByMemberId(new GetPointHistoryRequest(memberId))
			.getBody()
			.pointHistoryResponseList()
			.stream()
			.mapToLong(GetPointHistoryResponse::pointAmount)
			.sum();

		GetDeliveryFeeResponse deliveryFeeInfo = deliveryFeeClient.getRecentDeliveryFee().getBody();

		List<String> dates = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			dates.add(LocalDate.now().plusDays(i).format(DateTimeFormatter.ofPattern("M/d (E)")));
		}

		List<GetMemberAddressResponse> memberAddresses = memberAddressClient.getMemberAddressAll()
			.getBody()
			.memberAddresses();

		return OrderViewDTO.builder()
			.isPackable(isPackage)
			.coupons(coupons)
			.wrappingPapers(papers)
			.memberAddresses(memberAddresses)
			.dates(dates)
			.myPoint(myPoint)
			.deliveryFeeInfo(deliveryFeeInfo)
			.build();
	}

	@Override
	public void createOrder(PaymentRequest request) {
		// TODO: 여러번 실행되는 현상을 방지해야함

		// 트랜잭션을 invoke 함
		ordersSagaClient.createOrders(request);
	}



	@Override
	public Boolean existOrderUUID(UUID ordersUUID) {
		return redisOrderRepository.existsByOrderUUID(ordersUUID);
	}
}

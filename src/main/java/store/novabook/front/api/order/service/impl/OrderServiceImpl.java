package store.novabook.front.api.order.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
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
import store.novabook.front.api.member.member.dto.response.GetMemberResponse;
import store.novabook.front.api.member.member.dto.response.MemberOrderNameReponse;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.api.order.client.WrappingPaperClient;
import store.novabook.front.api.order.dto.request.PaymentRequest;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;
import store.novabook.front.api.order.service.OrderClient;
import store.novabook.front.api.order.service.OrderService;
import store.novabook.front.api.order.service.OrdersSagaClient;
import store.novabook.front.api.point.dto.request.GetPointHistoryRequest;
import store.novabook.front.api.point.dto.response.GetPointHistoryResponse;
import store.novabook.front.api.point.service.PointHistoryClient;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.order.dto.GetOrdersAdminResponse;
import store.novabook.front.store.order.dto.OrderTemporaryForm;
import store.novabook.front.store.order.dto.OrderViewDTO;
import store.novabook.front.store.order.dto.UpdateOrdersAdminRequest;
import store.novabook.front.store.order.repository.RedisOrderNonMemberRepository;
import store.novabook.front.store.order.repository.RedisOrderRepository;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

	private final OrderClient orderClient;
	public static final String ORDER_LOCK = "orderLock";
	private final WrappingPaperClient wrappingPaperClient;
	private final CouponClient couponClient;
	private final CategoryClient categoryClient;
	private final MemberCouponClient memberCouponClient;
	private final PointHistoryClient pointHistoryClient;
	private final MemberAddressClient memberAddressClient;
	private final DeliveryFeeClient deliveryFeeClient;
	private final MemberClient memberClient;

	private final OrdersSagaClient ordersSagaClient;
	private final RedisOrderNonMemberRepository redisOrderNonMemberRepository;
	private final RedisOrderRepository redisOrderRepository;
	private final RedisTemplate<String, String> redisTemplate;

	/**
	 * 주문 페이지 정보를 가져오는 로직
	 * @param bookDTOS
	 * @param memberId
	 * @return
	 */
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
			if (categoryClient.getCategoryByBId(bookId).getBody() == null) {
				throw new NullPointerException("CategoryResponse body is null for bookId: " + bookId);
			}
			List<Long> categoryIds = categoryClient.getCategoryByBId(bookId).getBody().categoryIds();
			categoryIdList.addAll(categoryIds);
		});


		List<GetWrappingPaperResponse> papers = wrappingPaperClient.getWrappingPaperAllList()
			.getBody()
			.getWrappingPaperResponse();

		GetDeliveryFeeResponse deliveryFeeInfo = deliveryFeeClient.getRecentDeliveryFee().getBody();

		List<String> dates = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			dates.add(LocalDate.now().plusDays(i).format(DateTimeFormatter.ofPattern("M/d (E)")));
		}


		if (memberId != null) {
			List<Long> couponIdList = memberCouponClient.getMemberCoupon().getBody().couponIds();

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
		} else {
			return OrderViewDTO.builder()
				.isPackable(isPackage)
				.wrappingPapers(papers)
				.dates(dates)
				.deliveryFeeInfo(deliveryFeeInfo)
				.build();
		}
	}


	/**
	 * 분산락 매커니즘 사용
	 * 여러번 주문 트랜잭션이 실행되는 것을 방지함
	 * @param request
	 */
	@Override
	public void createOrder(PaymentRequest request) {
		String lockKey = ORDER_LOCK + ":" + request.orderCode();

		boolean lockAcquired = Boolean.TRUE.equals(
			redisTemplate.opsForValue().setIfAbsent(lockKey, "lock", Duration.ofMinutes(60)));

		if (lockAcquired) {
			ordersSagaClient.createOrders(request);
		}
	}

	/**
	 * 주문서가 존재하는지 체크
	 * @param orderCode
	 * @param orderMemberId
	 * @return
	 */
	@Override
	public boolean isInvalidAccess(Long memberId, String orderCode, Long orderMemberId) {
		if (memberId == null) {
			return !redisOrderNonMemberRepository.existsById(orderCode);
		} else {
			Optional<OrderTemporaryForm> temporaryForm = redisOrderRepository.findById(memberId);
			if (temporaryForm.isEmpty()) {
				throw new IllegalArgumentException("조회되는 주문서가 없습니다.");
			}
			return !Objects.equals(memberId, orderMemberId) && temporaryForm.get().orderCode().equals(orderCode);
		}
	}

	/**
	 * @param orderCode
	 * @return 주문이 완료된 회원이름을 반환
	 */
	@Override
	public MemberOrderNameReponse getSuccessView(String orderCode) {
		// 비회원일때
		MemberOrderNameReponse memberResponse;
		if (memberClient.getMember().getBody() ==  null) {
			memberResponse = MemberOrderNameReponse.builder()
				.name("비회원")
				.orderCode(orderCode)
				.build();
		} else {
			GetMemberResponse memberInfo = memberClient.getMember().getBody();
			memberResponse = MemberOrderNameReponse.builder()
				.orderCode(orderCode)
				.name(memberInfo.name()).build();
		}
		return memberResponse;
	}

	@Override
	public PageResponse<GetOrdersAdminResponse> getOrderAllAdmin(int page, int size) {
		return orderClient.getOrdersAdmin(page, size);
	}

	@Override
	public void update(Long id, UpdateOrdersAdminRequest request) {
		orderClient.update(id, request);
	}


}

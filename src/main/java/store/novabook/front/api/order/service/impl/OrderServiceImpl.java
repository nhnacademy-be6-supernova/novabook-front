package store.novabook.front.api.order.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.api.coupon.client.CouponClient;
import store.novabook.front.api.delivery.client.DeliveryFeeClient;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressResponse;
import store.novabook.front.api.member.address.service.MemberAddressClient;
import store.novabook.front.api.member.coupon.service.MemberCouponClient;
import store.novabook.front.api.member.member.dto.response.GetMemberResponse;
import store.novabook.front.api.member.member.dto.response.MemberOrderNameReponse;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.api.order.client.WrappingPaperClient;
import store.novabook.front.api.order.dto.PaymentType;
import store.novabook.front.api.order.dto.request.PaymentRequest;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;
import store.novabook.front.api.order.service.OrderClient;
import store.novabook.front.api.order.service.OrderService;
import store.novabook.front.api.order.service.OrdersSagaClient;
import store.novabook.front.api.point.service.PointHistoryClient;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.order.dto.GetOrdersAdminResponse;
import store.novabook.front.store.order.dto.GetOrdersResponse;
import store.novabook.front.store.order.dto.OrderTemporaryForm;
import store.novabook.front.store.order.dto.OrderViewDTO;
import store.novabook.front.store.order.dto.RequestPayCancelMessage;
import store.novabook.front.store.order.dto.UpdateOrdersAdminRequest;
import store.novabook.front.store.order.repository.RedisOrderNonMemberRepository;
import store.novabook.front.store.order.repository.RedisOrderRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderClient orderClient;
	public static final String NOVA_ORDERS_SAGA_EXCHANGE = "nova.orders.saga.exchange";
	public static final String ORDER_LOCK = "orderLock";
	private final WrappingPaperClient wrappingPaperClient;
	private final CouponClient couponClient;
	private final CategoryClient categoryClient;
	private final MemberCouponClient memberCouponClient;
	private final PointHistoryClient pointHistoryClient;
	private final MemberAddressClient memberAddressClient;
	private final DeliveryFeeClient deliveryFeeClient;
	private final MemberClient memberClient;

	private final RabbitTemplate rabbitTemplate;
	private final OrdersSagaClient ordersSagaClient;
	private final RedisOrderNonMemberRepository redisOrderNonMemberRepository;
	private final RedisOrderRepository redisOrderRepository;
	private final RedisTemplate<String, String> redisTemplate;

	private final MeterRegistry meterRegistry;

	// 매트릭 정의
	private Counter orderCounter;
	private Timer orderProcessingTimer;
	private Counter orderFailureCounter;

	// 생성자에서 메트릭을 초기화합니다.
	@PostConstruct
	public void initMetrics() {
		this.orderCounter = meterRegistry.counter("orders_total_count");
		this.orderProcessingTimer = meterRegistry.timer("order_processing_time");
		this.orderFailureCounter = meterRegistry.counter("orders_failure_count");
	}

	/**
	 * 주문 페이지 정보를 가져오는 로직
	 *
	 * @param bookDTOS 책id 수량 포함
	 * @param memberId 회원 식별번호
	 * @return 주문 페이지에 선택지를 보여주기 위한 값들
	 */
	@Override
	@Transactional(readOnly = true)
	public OrderViewDTO getOrder(List<BookDTO> bookDTOS, Long memberId) {
		boolean isPackage = false;

		for (BookDTO bookDTO : bookDTOS) {
			if (bookDTO.isPackage()) {
				isPackage = true;
				break;
			}
		}

		List<GetWrappingPaperResponse> papers = wrappingPaperClient.getWrappingPaperAllList()
			.getBody()
			.getWrappingPaperResponse();

		GetDeliveryFeeResponse deliveryFeeInfo = deliveryFeeClient.getRecentDeliveryFee().getBody();

		List<String> dates = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			dates.add(LocalDate.now().plusDays(i).format(DateTimeFormatter.ofPattern("M/d (E)")));
		}

		if (memberId != null) {
			long myPoint = pointHistoryClient.getPointTotalByMemberId().getBody().pointAmount();

			List<GetMemberAddressResponse> memberAddresses = memberAddressClient.getMemberAddressAll()
				.getBody()
				.memberAddresses();

			return OrderViewDTO.builder()
				.isPackable(isPackage)
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
	 *
	 * @param request
	 */
	@Override
	public void createOrder(PaymentRequest request) {
		String lockKey = ORDER_LOCK + ":" + request.orderCode();

		boolean lockAcquired = Boolean.TRUE.equals(
			redisTemplate.opsForValue().setIfAbsent(lockKey, "lock", Duration.ofMinutes(60)));

		// if (lockAcquired) {
		// 	ordersSagaClient.createOrders(request);
		// }
		Timer.Sample sample = Timer.start(meterRegistry); // 타이머 시작

		try {
			if (lockAcquired) {
				// 주문 생성 성공 카운터 증가
				orderCounter.increment();
				ordersSagaClient.createOrders(request);
			} else {
				// 주문 생성 실패 카운터 증가
				orderFailureCounter.increment();
			}
		} catch (Exception e) {
			// 주문 생성 실패 카운터 증가
			orderFailureCounter.increment();
			throw e; // 예외를 다시 던져서 트랜잭션을 롤백하도록 함
		} finally {
			// 처리 시간 기록
			sample.stop(orderProcessingTimer);
		}
	}

	/**
	 * 주문서가 존재하는지 체크
	 *
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
		if (memberClient.getMember().getBody() == null) {
			memberResponse = MemberOrderNameReponse.builder().name("비회원").orderCode(orderCode).build();
		} else {
			GetMemberResponse memberInfo = memberClient.getMember().getBody();
			memberResponse = MemberOrderNameReponse.builder().orderCode(orderCode).name(memberInfo.name()).build();
		}
		return memberResponse;
	}

	@Override
	@Transactional
	public void sendRequestPayCancel(Long orderId) {
		// 상태 변경
		GetOrdersResponse ordersResponse = orderClient.getOrders(orderId).getBody();

		RequestPayCancelMessage message = RequestPayCancelMessage.builder()
			.paymentType(PaymentType.TOSS) //임시
			.totalAmount(ordersResponse.totalAmount())
			.orderCode(ordersResponse.code())
			.couponId(ordersResponse.couponId())
			.earnPointAmount(ordersResponse.pointSaveAmount())
			.paymentKey(ordersResponse.paymentKey())
			.usePointAmount(ordersResponse.usePointAmount())
			.memberId(ordersResponse.memberId())
			.status("PROCESS_SEND_PAY_MESSAGE")
			.build();

		// 5L 주문 취소 환불 상태로 변경
		orderClient.update(orderId, new UpdateOrdersAdminRequest(5L));
		rabbitTemplate.convertAndSend(NOVA_ORDERS_SAGA_EXCHANGE, "pay.cancel.routing.key", message);
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

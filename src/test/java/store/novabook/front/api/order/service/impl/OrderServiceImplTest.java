package store.novabook.front.api.order.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import store.novabook.front.api.category.dto.response.GetCategoryIdsByBookIdResponse;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.api.coupon.client.CouponClient;
import store.novabook.front.api.coupon.domain.CouponStatus;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;
import store.novabook.front.api.coupon.dto.request.GetCouponAllRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponResponse;
import store.novabook.front.api.delivery.client.DeliveryFeeClient;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressListResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressResponse;
import store.novabook.front.api.member.address.service.MemberAddressClient;
import store.novabook.front.api.member.coupon.dto.GetCouponIdsResponse;
import store.novabook.front.api.member.coupon.service.MemberCouponClient;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.api.order.client.WrappingPaperClient;
import store.novabook.front.api.order.dto.response.GetWrappingPaperAllResponse;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;
import store.novabook.front.api.order.service.OrderClient;
import store.novabook.front.api.order.service.OrdersSagaClient;
import store.novabook.front.api.point.dto.request.GetPointHistoryRequest;
import store.novabook.front.api.point.dto.response.GetPointHistoryListResponse;
import store.novabook.front.api.point.dto.response.GetPointHistoryResponse;
import store.novabook.front.api.point.service.PointHistoryClient;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.order.dto.GetOrdersAdminResponse;
import store.novabook.front.store.order.dto.GetOrdersResponse;
import store.novabook.front.store.order.dto.OrderViewDTO;
import store.novabook.front.store.order.dto.RequestPayCancelMessage;
import store.novabook.front.store.order.dto.UpdateOrdersAdminRequest;
import store.novabook.front.store.order.repository.RedisOrderNonMemberRepository;
import store.novabook.front.store.order.repository.RedisOrderRepository;

class OrderServiceImplTest {

	@Mock
	private OrderClient orderClient;

	@Mock
	private WrappingPaperClient wrappingPaperClient;

	@Mock
	private CouponClient couponClient;

	@Mock
	private CategoryClient categoryClient;

	@Mock
	private MemberCouponClient memberCouponClient;

	@Mock
	private PointHistoryClient pointHistoryClient;

	@Mock
	private MemberAddressClient memberAddressClient;

	@Mock
	private DeliveryFeeClient deliveryFeeClient;

	@Mock
	private MemberClient memberClient;

	@Mock
	private RabbitTemplate rabbitTemplate;

	@Mock
	private OrdersSagaClient ordersSagaClient;

	@Mock
	private RedisOrderNonMemberRepository redisOrderNonMemberRepository;

	@Mock
	private RedisOrderRepository redisOrderRepository;

	@Mock
	private RedisTemplate<String, String> redisTemplate;

	@InjectMocks
	private OrderServiceImpl orderService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetOrder() {
		List<BookDTO> bookDTOS = new ArrayList<>();
		BookDTO bookDTO = BookDTO.builder()
			.id(1L)
			.imageUrl("https://example.com/image.jpg")
			.name("Effective Java")
			.price(4500L)
			.quantity(100L)
			.discount(500L)
			.isPackage(false)
			.build();

		bookDTOS.add(bookDTO);

		Long memberId = 1L;
		List<Long> categoryIds = List.of(1L, 2L);
		GetCategoryIdsByBookIdResponse getCategoryIdsByBookIdResponse = new GetCategoryIdsByBookIdResponse(categoryIds);

		GetWrappingPaperResponse wrappingPaperResponse = GetWrappingPaperResponse.builder()
			.id(3L)
			.price(1200L)
			.name("Birthday Theme")
			.status("Available")
			.createdAt(LocalDateTime.of(2023, 11, 1, 0, 0))
			.updatedAt(LocalDateTime.of(2023, 11, 5, 0, 0))
			.build();
		List<GetWrappingPaperResponse> getWrappingPaperResponse = List.of(wrappingPaperResponse);
		GetWrappingPaperAllResponse getWrappingPaperAllResponse = new GetWrappingPaperAllResponse(
			getWrappingPaperResponse);

		GetDeliveryFeeResponse deliveryFeeResponse = GetDeliveryFeeResponse.builder()
			.id(1L)
			.fee(3000L)
			.createdAt(LocalDateTime.of(2023, 10, 10, 12, 0))
			.updatedAt(LocalDateTime.of(2023, 10, 15, 12, 0))
			.build();

		List<Long> couponIds = List.of(1L, 2L);
		GetCouponIdsResponse couponIdsResponse = new GetCouponIdsResponse(couponIds);

		GetCouponResponse couponResponse = GetCouponResponse.builder()
			.id(1L)
			.type(CouponType.GENERAL)
			.status(CouponStatus.UNUSED)
			.name("Summer Sale")
			.discountAmount(20)
			.discountType(DiscountType.PERCENT)
			.maxDiscountAmount(5000L)
			.minPurchaseAmount(10000L)
			.createdAt(LocalDateTime.of(2023, 6, 1, 0, 0))
			.expirationAt(LocalDateTime.of(2023, 9, 1, 0, 0))
			.usedAt(null)
			.build();
		List<GetCouponResponse> couponResponseList = List.of(couponResponse);
		GetCouponAllResponse getCouponAllResponse = new GetCouponAllResponse(couponResponseList);

		GetPointHistoryResponse getPointHistoryResponse = GetPointHistoryResponse.builder()
			.pointContent("Purchase Reward")
			.pointAmount(500L)
			.createdAt(LocalDateTime.of(2023, 10, 15, 12, 0))
			.build();
		List<GetPointHistoryResponse> pointHistoryResponseList = List.of(getPointHistoryResponse);
		GetPointHistoryListResponse getPointHistoryListResponse = new GetPointHistoryListResponse(
			pointHistoryResponseList);

		GetMemberAddressResponse getMemberAddressResponse = GetMemberAddressResponse.builder()
			.id(1L)
			.streetAddressesId(101L)
			.memberId(501L)
			.zipcode("12345")
			.nickname("Home")
			.streetAddresses("123 Main St")
			.memberAddressDetail("Apt 101")
			.build();
		List<GetMemberAddressResponse> memberAddresses = List.of(getMemberAddressResponse);
		GetMemberAddressListResponse getMemberAddressListResponse = new GetMemberAddressListResponse(memberAddresses);

		when(categoryClient.getCategoryByBId(anyLong())).thenReturn(
			new ApiResponse<>("SUCCESS", true, getCategoryIdsByBookIdResponse));
		when(wrappingPaperClient.getWrappingPaperAllList()).thenReturn(
			new ApiResponse<>("SUCCESS", true, getWrappingPaperAllResponse));
		when(deliveryFeeClient.getRecentDeliveryFee()).thenReturn(
			new ApiResponse<>("SUCCESS", true, deliveryFeeResponse));
		when(memberCouponClient.getMemberCoupon()).thenReturn(new ApiResponse<>("SUCCESS", true, couponIdsResponse));
		when(couponClient.getSufficientCouponAll(any(GetCouponAllRequest.class))).thenReturn(
			new ApiResponse<>("SUCCESS", true, getCouponAllResponse));
		when(pointHistoryClient.getPointHistoryListByMemberId(any(GetPointHistoryRequest.class))).thenReturn(
			new ApiResponse<>("SUCCESS", true, getPointHistoryListResponse));
		when(memberAddressClient.getMemberAddressAll()).thenReturn(
			new ApiResponse<>("SUCCESS", true, getMemberAddressListResponse));

		OrderViewDTO result = orderService.getOrder(bookDTOS, memberId);

		assertNotNull(result);
		verify(categoryClient, times(2)).getCategoryByBId(anyLong());
		verify(wrappingPaperClient, times(1)).getWrappingPaperAllList();
		verify(deliveryFeeClient, times(1)).getRecentDeliveryFee();
		verify(memberCouponClient, times(1)).getMemberCoupon();
		verify(couponClient, times(1)).getSufficientCouponAll(any(GetCouponAllRequest.class));
		verify(pointHistoryClient, times(1)).getPointHistoryListByMemberId(any(GetPointHistoryRequest.class));
		verify(memberAddressClient, times(1)).getMemberAddressAll();
	}

	@Test
	void testSendRequestPayCancel() {
		Long orderId = 1L;

		GetOrdersResponse getOrdersResponse = GetOrdersResponse.builder()
			.code("ORD123456")
			.memberId(1L)
			.deliveryFeeId(101L)
			.wrappingPaperId(201L)
			.ordersStatusId(1L)
			.ordersDate(LocalDateTime.of(2023, 10, 15, 12, 0))
			.totalAmount(15000L)
			.deliveryDate(LocalDateTime.of(2023, 10, 16, 12, 0))
			.bookPurchaseAmount(12000L)
			.deliveryAddress("123 Main St, Anytown, Anycountry")
			.couponId(301L)
			.usePointAmount(500L)
			.pointSaveAmount(150L)
			.paymentKey("pay_abcdef1234567890")
			.receiverName("John Doe")
			.receiverNumber("01012345678")
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
		when(orderClient.getOrders(anyLong())).thenReturn(new ApiResponse<>("SUCCESS", true, getOrdersResponse));

		orderService.sendRequestPayCancel(orderId);

		verify(orderClient, times(1)).getOrders(anyLong());
		verify(orderClient, times(1)).update(anyLong(), any(UpdateOrdersAdminRequest.class));
		verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any(RequestPayCancelMessage.class));
	}

	@Test
	void testGetOrderAllAdmin() {
		GetOrdersAdminResponse getOrdersAdminResponse = GetOrdersAdminResponse.builder()
			.ordersId(1L)
			.memberLoginId("user123")
			.ordersStatusId(2L)
			.ordersDate(LocalDateTime.of(2023, 10, 15, 12, 0))
			.totalAmount(15000L)
			.createdAt(LocalDateTime.now())
			.build();
		List<GetOrdersAdminResponse> data = new ArrayList<>();
		data.add(getOrdersAdminResponse);
		int page = 0;
		int size = 10;
		PageResponse<GetOrdersAdminResponse> response = new PageResponse<>(1, 10, 30, data);
		when(orderClient.getOrdersAdmin(anyInt(), anyInt())).thenReturn(response);

		PageResponse<GetOrdersAdminResponse> result = orderService.getOrderAllAdmin(page, size);

		assertEquals(response, result);
		verify(orderClient, times(1)).getOrdersAdmin(anyInt(), anyInt());
	}

	@Test
	void testUpdate() {
		Long id = 1L;
		UpdateOrdersAdminRequest request = new UpdateOrdersAdminRequest(1L);

		orderService.update(id, request);

		verify(orderClient, times(1)).update(id, request);
	}
}

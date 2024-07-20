package store.novabook.front.store.mypage.order;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.member.grade.dto.GetMemberGradeResponse;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.order.service.OrdersBookService;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.store.order.dto.GetOrderDetailResponse;
import store.novabook.front.store.order.dto.GetOrdersBookResponse;

@ExtendWith(MockitoExtension.class)
class MypageOrderControllerTest {

	@Mock
	private MemberGradeService memberGradeService;

	@Mock
	private OrdersBookService ordersBookService;

	@InjectMocks
	private MypageOrderController mypageOrderController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(mypageOrderController).build();
	}

	@Test
	void testGetOrderAll() throws Exception {

		GetOrdersBookResponse getOrdersBookResponse = new GetOrdersBookResponse(
			1L, // ordersId
			"The Pragmatic Programmer", // firstBookTitle
			2L, // extraBookCount
			5000L, // totalAmount
			"Shipped", // orderStatus
			LocalDateTime.now() // createdAt
		);
		List<GetOrdersBookResponse> data = new ArrayList<>();
		data.add(getOrdersBookResponse);
		PageResponse<GetOrdersBookResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);


		when(ordersBookService.getOrdersBookAll(0, 10)).thenReturn(expectedResponse);
		when(memberGradeService.getMemberGrade()).thenReturn(new GetMemberGradeResponse("Gold"));

		mockMvc.perform(get("/mypage/orders"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/mypage/order/order_list"))
			.andExpect(model().attributeExists("orders"))
			.andExpect(model().attributeExists("grade"));
	}

	@Test
	void testGetOrderDetail() throws Exception {

		GetOrderDetailResponse getOrderDetailResponse = GetOrderDetailResponse.builder()
			.ordersId(1L)
			.ordersStatusId(2L)
			.ordersStatusName("Shipped")
			.bookTitle(List.of("Effective Java", "Clean Code"))
			.quantity(2)
			.deliveryFee(500L)
			.wrappingFee(200L)
			.receiverName("John Doe")
			.receiverNumber("010-1234-5678")
			.receiverAddress("1234 Main St, Anytown, Anycountry")
			.expectedDeliveryDate(LocalDateTime.now().plusDays(5))
			.totalPrice(10000L)
			.couponDiscountAmount(1000L)
			.finalAmount(9000L)
			.pointSaveAmount(450L)
			.build();
		Long ordersId = 1L;
		when(ordersBookService.getOrderDetail(ordersId)).thenReturn(getOrderDetailResponse);
		when(memberGradeService.getMemberGrade()).thenReturn(new GetMemberGradeResponse("Gold"));

		mockMvc.perform(get("/mypage/orders/detail/{ordersId}", ordersId))
			.andExpect(status().isOk())
			.andExpect(view().name("store/mypage/order/order_list_detail"))
			.andExpect(model().attributeExists("ordersDetail"))
			.andExpect(model().attributeExists("grade"));
	}

	@Test
	void testGetOrderCancelAll() throws Exception {
		when(memberGradeService.getMemberGrade()).thenReturn(new GetMemberGradeResponse("Gold"));

		mockMvc.perform(get("/mypage/orders/cancel"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/mypage/order/order_cancel_list"))
			.andExpect(model().attributeExists("grade"));
	}
}

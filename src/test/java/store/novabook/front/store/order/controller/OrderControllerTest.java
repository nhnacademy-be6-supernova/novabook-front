package store.novabook.front.store.order.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.member.member.dto.response.MemberOrderNameReponse;
import store.novabook.front.api.order.dto.PaymentType;
import store.novabook.front.api.order.dto.request.PaymentRequest;
import store.novabook.front.api.order.dto.request.TossPaymentRequest;
import store.novabook.front.api.order.service.OrderService;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	private MockMvc mockMvc;


	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	@Test
	void testGetTossOrderSuccessPage() throws Exception {

		MemberOrderNameReponse memberOrderNameReponse = MemberOrderNameReponse.builder()
			.name("Alice Johnson")
			.orderCode("ORD20231005")
			.build();


		Long memberId = 1L;
		Long orderMemberId = 1L;
		String orderCode = "orderCode";
		TossPaymentRequest tossPaymentRequest = new TossPaymentRequest(5000L, "paymentKey123");

		when(orderService.isInvalidAccess(memberId, orderCode, orderMemberId)).thenReturn(false);
		when(orderService.getSuccessView(orderCode)).thenReturn(memberOrderNameReponse);

		mockMvc.perform(get("/orders/order/toss/success")
				.param("memberId", String.valueOf(orderMemberId))
				.param("orderId", orderCode)
				.flashAttr("tossPaymentRequest", tossPaymentRequest))
			.andExpect(status().isOk())
			.andExpect(view().name("store/order/order_success"))
			.andExpect(model().attributeExists("memberDto"));

		verify(orderService).isInvalidAccess(memberId, orderCode, orderMemberId);
		verify(orderService).createOrder(
			new PaymentRequest(PaymentType.TOSS, orderMemberId, orderCode, tossPaymentRequest));
		verify(orderService).getSuccessView(orderCode);
	}

	@Test
	void testGetOrderFailPage() throws Exception {
		mockMvc.perform(get("/orders/order/toss/fail"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/order/order_fail"));
	}
}

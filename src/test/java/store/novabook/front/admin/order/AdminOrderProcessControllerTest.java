package store.novabook.front.admin.point;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.admin.order.AdminOrderProcessController;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.order.service.OrderService;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;
import store.novabook.front.store.order.dto.GetOrdersAdminResponse;
import store.novabook.front.store.order.dto.UpdateOrdersAdminRequest;

@WebMvcTest(AdminOrderProcessController.class)
class AdminOrderProcessControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new AdminOrderProcessController(orderService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void testGetOrderAll() throws Exception {

		GetOrdersAdminResponse getOrdersAdminResponse = GetOrdersAdminResponse.builder()
			.ordersId(1L)
			.memberLoginId("user123")
			.ordersStatusId(2L)
			.ordersDate(LocalDateTime.now())
			.totalAmount(5000L)
			.createdAt(LocalDateTime.now())
			.build();
		List<GetOrdersAdminResponse> data = List.of(getOrdersAdminResponse);
		PageResponse<GetOrdersAdminResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);

		when(orderService.getOrderAllAdmin(anyInt(), anyInt())).thenReturn(expectedResponse);

		mockMvc.perform(get("/admin/orders")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/order/order_process_list"))
			.andExpect(model().attribute("orders", expectedResponse))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testUpdateOrder() throws Exception {
		UpdateOrdersAdminRequest request = new UpdateOrdersAdminRequest(1234L);
		doNothing().when(orderService).update(anyLong(), any(UpdateOrdersAdminRequest.class));

		mockMvc.perform(post("/admin/orders/1")
					.contentType("application/x-www-form-urlencoded")
					.param("ordersStatusId", String.valueOf(request.ordersStatusId()))
			)
			.andExpect(status().isNoContent())
			.andDo(MockMvcResultHandlers.print());

		verify(orderService).update(anyLong(), any(UpdateOrdersAdminRequest.class));
	}
}

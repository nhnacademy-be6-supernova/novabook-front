package store.novabook.front.admin.delivery;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.delivery.dto.request.CreateDeliveryFeeRequest;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.delivery.service.DeliveryFeeService;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@WebMvcTest(AdminDeliveryController.class)
class AdminDeliveryControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private DeliveryFeeService deliveryFeeService;


	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new AdminDeliveryController(deliveryFeeService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void testGetDeliveryForm() throws Exception {
		GetDeliveryFeeResponse getDeliveryFeeResponse = GetDeliveryFeeResponse.builder()
			.id(1L)
			.fee(5000L)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now().plusHours(2))
			.build();

		List<GetDeliveryFeeResponse> data = new ArrayList<>();
		data.add(getDeliveryFeeResponse);

		PageResponse<GetDeliveryFeeResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);

		when(deliveryFeeService.getDeliveryFeeAllPage(anyInt(), anyInt()))
			.thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/admin/deliveries/delivery/form")
				.param("page", "0")
				.param("size", "5"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/delivery/delivery_fee_form"))
			.andExpect(model().attributeExists("deliveries"))
			.andExpect(model().attribute("deliveries", expectedResponse))
			.andDo(MockMvcResultHandlers.print());
	}


	@Test
	void testCreateDelivery() throws Exception {
		mockMvc.perform(post("/admin/deliveries/delivery"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/admin/deliveries/delivery/form"))
			.andDo(MockMvcResultHandlers.print());

		verify(deliveryFeeService).createDeliveryFee(any(CreateDeliveryFeeRequest.class));
	}
}

package store.novabook.front.store.coupon;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.coupon.dto.response.GetLimitedCouponTemplateResponse;
import store.novabook.front.api.coupon.service.CouponService;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CouponController.class)
@AutoConfigureMockMvc
class CouponControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CouponService couponService;

	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new CouponController(couponService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void testGetLimitedCouponAll() throws Exception {
		List<GetLimitedCouponTemplateResponse> mockLimitedCouponList = Collections.emptyList();
		PageResponse<GetLimitedCouponTemplateResponse> mockLimitedPageResponse = PageResponse.success(0, 5, 10, mockLimitedCouponList);

		when(couponService.getLimitedCouponTemplateAll(eq(true), anyInt(), anyInt()))
			.thenReturn(mockLimitedPageResponse);

		mockMvc.perform(get("/coupons/limited"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/coupon/limited_coupon_list"))
			.andExpect(model().attributeExists("limitedCouponList"));

		verify(couponService, times(1)).getLimitedCouponTemplateAll(eq(true), anyInt(), anyInt());
		verifyNoMoreInteractions(couponService);
	}
}

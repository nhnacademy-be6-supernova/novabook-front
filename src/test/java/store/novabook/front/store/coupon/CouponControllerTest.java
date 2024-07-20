package store.novabook.front.store.coupon;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponTemplateResponse;
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
	void testGetCouponAll() throws Exception {
		List<GetCouponTemplateResponse> mockGeneralCouponList = Collections.emptyList();
		PageResponse<GetCouponTemplateResponse> mockGeneralPageResponse = PageResponse.success(0, 5, 10, mockGeneralCouponList);

		GetCategoryCouponTemplateResponse categoryCouponTemplateResponse = new GetCategoryCouponTemplateResponse(
			1L,
			2L,
			CouponType.GENERAL,
			"Back to School Sale",
			200,
			DiscountType.PERCENT,
			1000,
			3000,
			LocalDateTime.now(),
			LocalDateTime.now().plusDays(30),
			30
		);

		List<GetCategoryCouponTemplateResponse> data = new ArrayList<>();
		data.add(categoryCouponTemplateResponse);

		PageResponse<GetCategoryCouponTemplateResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);

		when(couponService.getCouponTemplateAll(eq(CouponType.GENERAL), eq(true), anyInt(), anyInt(), isNull()))
			.thenReturn(mockGeneralPageResponse);

		when(couponService.getCategoryCouponTemplateAll(anyBoolean(), anyInt(), anyInt()))
			.thenReturn(expectedResponse);

		mockMvc.perform(get("/coupons")
				.param("generalPage", "0")
				.param("categoryPage", "0")
				.param("size", "5"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/coupon/coupon_book"))
			.andExpect(model().attributeExists("generalCouponList"))
			.andExpect(model().attributeExists("categoryCouponList"));

		verify(couponService, times(1))
			.getCouponTemplateAll(eq(CouponType.GENERAL), eq(true), eq(0), eq(5), isNull());
		verify(couponService, times(1))
			.getCategoryCouponTemplateAll(anyBoolean(), anyInt(), anyInt());
		verifyNoMoreInteractions(couponService);
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

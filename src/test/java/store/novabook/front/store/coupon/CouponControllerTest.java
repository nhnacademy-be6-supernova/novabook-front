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

import store.novabook.front.api.coupon.domain.CouponType;
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

	// @Test
	// void testGetCouponAll() throws Exception {
	// 	// Mock data
	// 	List<GetCouponTemplateResponse> mockCouponList = Collections.emptyList();
	// 	PageResponse<GetCouponTemplateResponse> mockGeneralPageResponse = PageResponse.success(0, 5, 10, mockCouponList);
	// 	List<GetCategoryCouponTemplateResponse> mockCouponList2 = Collections.emptyList();
	// 	PageResponse<GetCategoryCouponTemplateResponse> mockCategoryPageResponse = PageResponse.success(0, 5, 10, mockCouponList2);
	//
	// 	// Mock couponService methods
	// 	when(couponService.getCouponTemplateAll(eq(CouponType.GENERAL), eq(true), anyInt(), anyInt(), null))
	// 		.thenReturn(mockGeneralPageResponse);
	// 	when(couponService.getCategoryCouponTemplateAll(eq(true), anyInt(), anyInt()))
	// 		.thenReturn(mockCategoryPageResponse);
	//
	// 	// Perform GET request to "/coupons"
	// 	mockMvc.perform(get("/coupons"))
	// 		.andExpect(status().isOk()) // Expect HTTP 200 OK status
	// 		.andExpect(view().name("store/coupon/coupon_book")) // Expect view name to be "store/coupon/coupon_book"
	// 		.andExpect(model().attributeExists("generalCouponList")) // Expect "generalCouponList" attribute in the model
	// 		.andExpect(model().attributeExists("categoryCouponList")); // Expect "categoryCouponList" attribute in the model
	//
	// 	// Verify interactions with couponService
	// 	verify(couponService, times(1)).getCouponTemplateAll(eq(CouponType.GENERAL), eq(true), anyInt(), anyInt(), null);
	// 	verify(couponService, times(1)).getCategoryCouponTemplateAll(eq(true), anyInt(), anyInt());
	// 	verifyNoMoreInteractions(couponService);
	// }

	@Test
	void testGetLimitedCouponAll() throws Exception {
		// Mock data
		List<GetLimitedCouponTemplateResponse> mockLimitedCouponList = Collections.emptyList();
		PageResponse<GetLimitedCouponTemplateResponse> mockLimitedPageResponse = PageResponse.success(0, 5, 10, mockLimitedCouponList);

		// Mock couponService method
		when(couponService.getLimitedCouponTemplateAll(eq(true), anyInt(), anyInt()))
			.thenReturn(mockLimitedPageResponse);

		// Perform GET request to "/coupons/limited"
		mockMvc.perform(get("/coupons/limited"))
			.andExpect(status().isOk()) // Expect HTTP 200 OK status
			.andExpect(view().name("store/coupon/limited_coupon_list")) // Expect view name to be "store/coupon/limited_coupon_list"
			.andExpect(model().attributeExists("limitedCouponList")); // Expect "limitedCouponList" attribute in the model

		// Verify interactions with couponService
		verify(couponService, times(1)).getLimitedCouponTemplateAll(eq(true), anyInt(), anyInt());
		verifyNoMoreInteractions(couponService);
	}
}

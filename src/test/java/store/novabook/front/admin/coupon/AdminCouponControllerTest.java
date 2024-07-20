package store.novabook.front.admin.coupon;

import static org.mockito.ArgumentMatchers.*;
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

import store.novabook.front.api.category.domain.Category;
import store.novabook.front.api.category.dto.SubCategoryDTO;
import store.novabook.front.api.category.dto.response.GetCategoryListResponse;
import store.novabook.front.api.category.dto.response.GetCategoryResponse;
import store.novabook.front.api.category.service.CategoryService;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;
import store.novabook.front.api.coupon.dto.request.CreateBookCouponTemPlateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCategoryCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateLimitedCouponTemplateRequest;
import store.novabook.front.api.coupon.service.CouponService;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@WebMvcTest(AdminCouponController.class)
class AdminCouponControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CouponService couponService;

	@MockBean
	private CategoryService categoryService;

	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new AdminCouponController(couponService, categoryService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void testGetCouponGeneralForm() throws Exception {
		mockMvc.perform(get("/admin/coupons/common/type")
				.param("type", CouponType.BIRTHDAY.name()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/coupon/coupon_common_form"))
			.andExpect(model().attribute("couponType", CouponType.BIRTHDAY))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testGetCouponBookForm() throws Exception {
		mockMvc.perform(get("/admin/coupons/book/form"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/admin/books"))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testGetCouponCategoryForm() throws Exception {


		Category category = Category.builder()
			.id(1L)
			.topCategory(null)
			.name("Fiction")
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		SubCategoryDTO subCategoryDTO1 = new SubCategoryDTO(2L, "Sub Category 1");
		SubCategoryDTO subCategoryDTO2 = new SubCategoryDTO(3L, "Sub Category 2");
		List<SubCategoryDTO> subCategories = List.of(subCategoryDTO1, subCategoryDTO2);

		GetCategoryResponse getCategoryResponse = GetCategoryResponse.fromEntity(category, subCategories);
		GetCategoryListResponse getCategoryListResponse = GetCategoryListResponse.builder()
			.categories(List.of(getCategoryResponse))
			.build();

		when(categoryService.getCategoryAll()).thenReturn(getCategoryListResponse);

		mockMvc.perform(get("/admin/coupons/category/form"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/coupon/coupon_category_form"))
			.andExpect(model().attributeExists("categories"))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testGetCouponLimitedForm() throws Exception {
		mockMvc.perform(get("/admin/coupons/limited/form"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/coupon/coupon_limited_form"))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testCreateCouponTemplateCommon() throws Exception {
		mockMvc.perform(post("/admin/coupons/common/create")
				.param("name", "Summer Sale")
				.param("type", CouponType.GENERAL.name())
				.param("discountAmount", "10")
				.param("discountType", DiscountType.PERCENT.name())
				.param("maxDiscountAmount", "5000")
				.param("minPurchaseAmount", "10000")
				.param("startedAt", "2023-06-01T00:00")
				.param("expirationAt", "2023-09-01T23:59")
				.param("usePeriod", "90"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/admin/coupons"))
			.andDo(MockMvcResultHandlers.print());

		verify(couponService).createGeneralTemplateCoupon(any(CreateCouponTemplateRequest.class));
	}

	@Test
	void testCreateCouponTemplateBook() throws Exception {
		mockMvc.perform(post("/admin/coupons/book/create")
				.param("bookId", "1")
				.param("name", "Bestseller Discount")
				.param("discountAmount", "500")
				.param("discountType", DiscountType.PERCENT.name())
				.param("maxDiscountAmount", "2000")
				.param("minPurchaseAmount", "10000")
				.param("startedAt", "2023-10-01T00:00")
				.param("expirationAt", "2023-12-31T23:59")
				.param("usePeriod", "90"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/admin/coupons"))
			.andDo(MockMvcResultHandlers.print());

		verify(couponService).createBookTemplateCoupon(any(CreateBookCouponTemPlateRequest.class));
	}


	@Test
	void testCreateCouponTemplateCategory() throws Exception {
		mockMvc.perform(post("/admin/coupons/category/create")
				.param("categoryId", "1")
				.param("name", "Category-Wide Discount")
				.param("discountAmount", "5000")
				.param("discountType", DiscountType.PERCENT.name())
				.param("maxDiscountAmount", "10000")
				.param("minPurchaseAmount", "20000")
				.param("startedAt", "2023-10-01T00:00")
				.param("expirationAt", "2023-12-31T23:59")
				.param("usePeriod", "90"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/admin/coupons"))
			.andDo(MockMvcResultHandlers.print());

		verify(couponService).createCategoryTemplateCoupon(any(CreateCategoryCouponTemplateRequest.class));
	}

	@Test
	void testCreateCouponTemplateLimited() throws Exception {
		mockMvc.perform(post("/admin/coupons/limited/create")
				.param("quantity", "50")
				.param("name", "Flash Sale 50% Off")
				.param("discountAmount", "50")
				.param("discountType", DiscountType.PERCENT.name())
				.param("maxDiscountAmount", "5000")
				.param("minPurchaseAmount", "1000")
				.param("startedAt", "2023-10-17T00:00")
				.param("expirationAt", "2023-10-19T23:59")
				.param("usePeriod", "15"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/admin/coupons"))
			.andDo(MockMvcResultHandlers.print());

		verify(couponService).createLimitedTemplateCoupon(any(CreateLimitedCouponTemplateRequest.class));
	}




}

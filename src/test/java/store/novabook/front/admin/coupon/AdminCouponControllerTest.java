package store.novabook.front.admin.coupon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.admin.category.AdminCategoryController;
import store.novabook.front.api.category.domain.Category;
import store.novabook.front.api.category.dto.SubCategoryDTO;
import store.novabook.front.api.category.dto.response.GetCategoryListResponse;
import store.novabook.front.api.category.dto.response.GetCategoryResponse;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;
import store.novabook.front.api.coupon.dto.request.CreateBookCouponTemPlateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCategoryCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateLimitedCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponTemplateResponse;
import store.novabook.front.api.coupon.service.CouponService;
import store.novabook.front.api.category.service.CategoryService;
import store.novabook.front.api.coupon.dto.response.GetLimitedCouponTemplateResponse;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	public void testGetCoupons() throws Exception {
		GetCouponTemplateResponse getCouponTemplateResponse = GetCouponTemplateResponse.builder()
			.id(1L)
			.type(CouponType.GENERAL)
			.name("10% Off Books")
			.discountAmount(10)
			.discountType(DiscountType.PERCENT)
			.maxDiscountAmount(5000)
			.minPurchaseAmount(10000)
			.startedAt(LocalDateTime.now())
			.expirationAt(LocalDateTime.now().plusDays(30))
			.usePeriod(30)
			.build();
		List<GetCouponTemplateResponse> data = new ArrayList<>();
		data.add(getCouponTemplateResponse);

		PageResponse<GetCouponTemplateResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);

		GetBookCouponTemplateResponse getBookCouponTemplateResponse = GetBookCouponTemplateResponse.builder()
			.bookId(1L)
			.id(2L)
			.type(CouponType.GENERAL)
			.name("20% Off Selected Books")
			.discountAmount(20)
			.discountType(DiscountType.PERCENT)
			.maxDiscountAmount(5000)
			.minPurchaseAmount(20000)
			.startedAt(LocalDateTime.now())
			.expirationAt(LocalDateTime.now().plusDays(30))
			.usePeriod(30)
			.build();

		List<GetBookCouponTemplateResponse> data2 = new ArrayList<>();
		data2.add(getBookCouponTemplateResponse);

		PageResponse<GetBookCouponTemplateResponse> expectedResponse2 = new PageResponse<>(1, 10, 30, data2);

		GetCategoryCouponTemplateResponse getCategoryCouponTemplateResponse = new GetCategoryCouponTemplateResponse(
			1L, // categoryId
			2L, // id
			CouponType.GENERAL, // type
			"25% Off Category Books", // name
			25, // discountAmount
			DiscountType.PERCENT, // discountType
			10000, // maxDiscountAmount
			50000, // minPurchaseAmount
			LocalDateTime.now(), // startedAt
			LocalDateTime.now().plusDays(30), // expirationAt,
			30
		);
		List<GetCategoryCouponTemplateResponse> data3 = new ArrayList<>();
		data3.add(getCategoryCouponTemplateResponse);

		PageResponse<GetCategoryCouponTemplateResponse> expectedResponse3 = new PageResponse<>(1, 10, 30, data3);


		GetLimitedCouponTemplateResponse getLimitedCouponTemplateResponse = GetLimitedCouponTemplateResponse.builder()
			.quantity(100L)
			.id(1L)
			.type(CouponType.GENERAL)
			.name("Limited Time Offer")
			.discountAmount(15)
			.discountType(DiscountType.PERCENT)
			.maxDiscountAmount(3000)
			.minPurchaseAmount(15000)
			.startedAt(LocalDateTime.now())
			.expirationAt(LocalDateTime.now().plusDays(15))
			.usePeriod(15)
			.build();
		List<GetLimitedCouponTemplateResponse> data4 = new ArrayList<>();
		data4.add(getLimitedCouponTemplateResponse);

		PageResponse<GetLimitedCouponTemplateResponse> expectedResponse4 = new PageResponse<>(1, 10, 30, data4);


		// Arrange
		when(couponService.getCouponTemplateAll(any(CouponType.class), any(Boolean.class), anyInt(), anyInt(), null))
			.thenReturn(expectedResponse);
		when(couponService.getBookCouponTemplateAll(anyInt(), anyInt()))
			.thenReturn(expectedResponse2);
		when(couponService.getCategoryCouponTemplateAll(any(Boolean.class), anyInt(), anyInt()))
			.thenReturn(expectedResponse3);
		when(couponService.getLimitedCouponTemplateAll(any(Boolean.class), anyInt(), anyInt()))
			.thenReturn(expectedResponse4);

		// Act and Assert
		mockMvc.perform(get("/admin/coupons"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/coupon/coupon_list"))
			.andExpect(model().attributeExists("birthdayCoupons"))
			.andExpect(model().attributeExists("welcomeCoupons"))
			.andExpect(model().attributeExists("generalCoupons"))
			.andExpect(model().attributeExists("bookCoupons"))
			.andExpect(model().attributeExists("categoryCoupons"))
			.andExpect(model().attributeExists("limitedCoupons"))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetCouponGeneralForm() throws Exception {
		// Act and Assert
		mockMvc.perform(get("/admin/coupons/common/type")
				.param("type", CouponType.BIRTHDAY.name()))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/coupon/coupon_common_form"))
			.andExpect(model().attribute("couponType", CouponType.BIRTHDAY))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetCouponBookForm() throws Exception {
		// Act and Assert
		mockMvc.perform(get("/admin/coupons/book/form"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/admin/books"))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetCouponCategoryForm() throws Exception {


		Category category = Category.builder()
			.id(1L)
			.topCategory(null) // Assuming this is a top-level category with no parent
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

		// Act and Assert
		mockMvc.perform(get("/admin/coupons/category/form"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/coupon/coupon_category_form"))
			.andExpect(model().attributeExists("categories"))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetCouponLimitedForm() throws Exception {
		// Act and Assert
		mockMvc.perform(get("/admin/coupons/limited/form"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/coupon/coupon_limited_form"))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateCouponTemplateCommon() throws Exception {
		// Arrange
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
	public void testCreateCouponTemplateBook() throws Exception {
		// Arrange
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
	public void testCreateCouponTemplateCategory() throws Exception {
		// Arrange
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
	public void testCreateCouponTemplateLimited() throws Exception {
		// Arrange
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

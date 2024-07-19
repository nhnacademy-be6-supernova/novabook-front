package store.novabook.front.api.coupon.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.coupon.client.CouponClient;
import store.novabook.front.api.coupon.domain.DiscountType;
import store.novabook.front.api.coupon.dto.request.*;
import store.novabook.front.api.coupon.dto.response.*;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;


class CouponServiceImplTest {

	@Mock
	private CouponClient couponClient;

	@InjectMocks
	private CouponServiceImpl couponService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetBookCouponTemplateAll() {
		GetBookCouponTemplateResponse bookCouponTemplateResponse = GetBookCouponTemplateResponse.builder()
			.bookId(1L)
			.id(100L)
			.type(CouponType.GENERAL)
			.name("Summer Sale")
			.discountAmount(500)
			.discountType(DiscountType.PERCENT)
			.maxDiscountAmount(1500)
			.minPurchaseAmount(3000)
			.startedAt(LocalDateTime.of(2023, 6, 1, 0, 0))
			.expirationAt(LocalDateTime.of(2023, 9, 1, 23, 59))
			.usePeriod(30)
			.build();


		List<GetBookCouponTemplateResponse> data = new ArrayList<>();
		data.add(bookCouponTemplateResponse);

		
		int page = 0;
		int size = 10;
		PageResponse<GetBookCouponTemplateResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);
		when(couponClient.getBookCouponTemplateAll(page, size)).thenReturn(expectedResponse);

		
		PageResponse<GetBookCouponTemplateResponse> actualResponse = couponService.getBookCouponTemplateAll(page, size);

		
		assertEquals(expectedResponse, actualResponse);
		verify(couponClient, times(1)).getBookCouponTemplateAll(page, size);
	}

	@Test
	void testGetCategoryCouponTemplateAll() {

		GetCategoryCouponTemplateResponse categoryCouponTemplateResponse = new GetCategoryCouponTemplateResponse(
			1L, // categoryId
			100L, // id
			CouponType.GENERAL, // type
			"Spring Special", // name
			1000, // discountAmount
			DiscountType.PERCENT, // discountType
			2000, // maxDiscountAmount
			5000, // minPurchaseAmount
			LocalDateTime.of(2023, 4, 1, 0, 0), // startedAt
			LocalDateTime.of(2023, 6, 30, 23, 59), // expirationAt
			60 // usePeriod
		);

		List<GetCategoryCouponTemplateResponse> data = new ArrayList<>();
		data.add(categoryCouponTemplateResponse);
		
		boolean isValid = true;
		int page = 0;
		int size = 10;
		PageResponse<GetCategoryCouponTemplateResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);
		when(couponClient.getCategoryCouponTemplateAll(isValid, page, size)).thenReturn(expectedResponse);

		
		PageResponse<GetCategoryCouponTemplateResponse> actualResponse =
			couponService.getCategoryCouponTemplateAll(isValid, page, size);

		
		assertEquals(expectedResponse, actualResponse);
		verify(couponClient, times(1)).getCategoryCouponTemplateAll(isValid, page, size);
	}

	@Test
	void testGetCouponTemplateAll() {
		GetCouponTemplateResponse getCouponTemplateResponse = GetCouponTemplateResponse.builder()
			.id(100L)
			.type(CouponType.GENERAL)
			.name("New Year Sale")
			.discountAmount(1000)
			.discountType(DiscountType.PERCENT)
			.maxDiscountAmount(2000)
			.minPurchaseAmount(5000)
			.startedAt(LocalDateTime.of(2023, 1, 1, 0, 0))
			.expirationAt(LocalDateTime.of(2023, 1, 31, 23, 59))
			.usePeriod(30)
			.build();

		List<GetCouponTemplateResponse> data = new ArrayList<>();
		data.add(getCouponTemplateResponse);
		
		CouponType type = CouponType.GENERAL;
		Boolean isValid = true;
		int page = 0;
		int size = 10;
		PageResponse<GetCouponTemplateResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);
		when(couponClient.getCouponTemplateAll(type, isValid, page, size,null)).thenReturn(expectedResponse);

		
		PageResponse<GetCouponTemplateResponse> actualResponse =
			couponService.getCouponTemplateAll(type, isValid, page, size,null);

		
		assertEquals(expectedResponse, actualResponse);
		verify(couponClient, times(1)).getCouponTemplateAll(type, isValid, page, size,null);
	}

	@Test
	void testGetLimitedCouponTemplateAll() {
		GetLimitedCouponTemplateResponse limitedCouponTemplateResponse = GetLimitedCouponTemplateResponse.builder()
			.quantity(100L)
			.id(200L)
			.type(CouponType.LIMITED)
			.name("Limited Time Offer")
			.discountAmount(2000)
			.discountType(DiscountType.PERCENT)
			.maxDiscountAmount(5000)
			.minPurchaseAmount(10000)
			.startedAt(LocalDateTime.of(2023, 10, 1, 0, 0))
			.expirationAt(LocalDateTime.of(2023, 12, 31, 23, 59))
			.usePeriod(90)
			.build();

		List<GetLimitedCouponTemplateResponse> data = new ArrayList<>();
		data.add(limitedCouponTemplateResponse);


		
		boolean isValid = true;
		int page = 0;
		int size = 10;
		PageResponse<GetLimitedCouponTemplateResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);
		when(couponClient.getLimitedCouponTemplateAll(isValid, page, size)).thenReturn(expectedResponse);

		
		PageResponse<GetLimitedCouponTemplateResponse> actualResponse =
			couponService.getLimitedCouponTemplateAll(isValid, page, size);

		
		assertEquals(expectedResponse, actualResponse);
		verify(couponClient, times(1)).getLimitedCouponTemplateAll(isValid, page, size);
	}

	@Test
	void testCreateGeneralTemplateCoupon() {
		
		CreateCouponTemplateRequest request = mock(CreateCouponTemplateRequest.class);

		
		couponService.createGeneralTemplateCoupon(request);

		
		verify(couponClient, times(1)).createCouponTemplate(request);
	}

	@Test
	void testCreateBookTemplateCoupon() {
		
		CreateBookCouponTemPlateRequest request = mock(CreateBookCouponTemPlateRequest.class);

		
		couponService.createBookTemplateCoupon(request);

		
		verify(couponClient, times(1)).createBookCouponTemplate(request);
	}

	@Test
	void testCreateCategoryTemplateCoupon() {
		
		CreateCategoryCouponTemplateRequest request = mock(CreateCategoryCouponTemplateRequest.class);

		
		couponService.createCategoryTemplateCoupon(request);

		
		verify(couponClient, times(1)).createCategoryCouponTemplate(request);
	}

	@Test
	void testCreateLimitedTemplateCoupon() {
		
		CreateLimitedCouponTemplateRequest request = mock(CreateLimitedCouponTemplateRequest.class);

		
		couponService.createLimitedTemplateCoupon(request);

		
		verify(couponClient, times(1)).createLimitedCouponTemplate(request);
	}

	@Test
	void testGetBookCouponTemplate() {

		GetBookCouponTemplateResponse bookCouponTemplateResponse = GetBookCouponTemplateResponse.builder()
			.bookId(1L)
			.id(101L)
			.type(CouponType.GENERAL)
			.name("Winter Wonderland Sale")
			.discountAmount(750)
			.discountType(DiscountType.PERCENT)
			.maxDiscountAmount(1500)
			.minPurchaseAmount(3000)
			.startedAt(LocalDateTime.of(2023, 12, 1, 0, 0))
			.expirationAt(LocalDateTime.of(2024, 1, 31, 23, 59))
			.usePeriod(60)
			.build();

		List<GetBookCouponTemplateResponse> responseList = new ArrayList<>();
		responseList.add(bookCouponTemplateResponse);
		
		Long bookId = 1L;
		boolean isValid = true;
		GetBookCouponTemplateAllResponse expectedResponse = new GetBookCouponTemplateAllResponse(responseList);
		ApiResponse<GetBookCouponTemplateAllResponse> apiResponse = new ApiResponse<>("SUCCESS", true, expectedResponse);
		when(couponClient.getCouponTemplateByBookId(bookId, isValid)).thenReturn(apiResponse);

		
		GetBookCouponTemplateAllResponse actualResponse = couponService.getBookCouponTemplate(bookId, isValid);

		
		assertEquals(expectedResponse, actualResponse);
		verify(couponClient, times(1)).getCouponTemplateByBookId(bookId, isValid);
	}

	@Test
	void testGetCategoryCouponTemplate() {
		GetCategoryCouponTemplateResponse categoryCouponTemplateResponse = new GetCategoryCouponTemplateResponse(
			2L, // categoryId
			102L, // id
			CouponType.LIMITED, // type
			"Autumn Deals", // name
			1200, // discountAmount
			DiscountType.PERCENT, // discountType
			3000, // maxDiscountAmount
			6000, // minPurchaseAmount
			LocalDateTime.of(2023, 10, 1, 0, 0), // startedAt
			LocalDateTime.of(2023, 11, 30, 23, 59), // expirationAt
			45 // usePeriod
		);

		List<GetCategoryCouponTemplateResponse> responseList = new ArrayList<>();
		responseList.add(categoryCouponTemplateResponse);
		
		List<Long> categoryIdList = Arrays.asList(1L, 2L);
		boolean isValid = true;
		GetCategoryCouponTemplateAllResponse expectedResponse = new GetCategoryCouponTemplateAllResponse(responseList);
		when(couponClient.getCategoryCouponTemplateAllByCategoryIdAll(categoryIdList, isValid))
			.thenReturn(expectedResponse);

		
		GetCategoryCouponTemplateAllResponse actualResponse =
			couponService.getCategoryCouponTemplate(categoryIdList, isValid);

		
		assertEquals(expectedResponse, actualResponse);
		verify(couponClient, times(1)).getCategoryCouponTemplateAllByCategoryIdAll(categoryIdList, isValid);
	}
}

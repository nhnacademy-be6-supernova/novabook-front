package store.novabook.front.api.member.member.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import store.novabook.front.api.coupon.domain.CouponStatus;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;
import store.novabook.front.api.coupon.dto.request.DownloadCouponRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponHistoryResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponResponse;
import store.novabook.front.api.coupon.dto.response.GetUsedCouponHistoryResponse;
import store.novabook.front.api.member.coupon.dto.DownloadCouponMessageRequest;
import store.novabook.front.api.member.coupon.service.MemberCouponClient;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

class MemberCouponServiceImplTest {

	@Mock
	private MemberClient memberClient;

	@Mock
	private MemberCouponClient memberCouponClient;

	@InjectMocks
	private MemberCouponServiceImpl memberCouponService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getMyCouponAllWithValid_ShouldReturnResponse() {
		List<GetCouponResponse> couponResponseList = new ArrayList<>();
		GetCouponResponse couponResponse = GetCouponResponse.builder()
			.id(1L)
			.type(CouponType.GENERAL)
			.status(CouponStatus.USED)
			.name("Summer Sale")
			.discountAmount(5000)
			.discountType(DiscountType.AMOUNT)
			.maxDiscountAmount(5000)
			.minPurchaseAmount(20000)
			.createdAt(LocalDateTime.now())
			.expirationAt(LocalDateTime.now().plusDays(30))
			.usedAt(null)
			.build();
		couponResponseList.add(couponResponse);
		GetCouponAllResponse expectedResponse = new GetCouponAllResponse(couponResponseList);
		when(memberCouponClient.getMemberCouponValidByMemberId()).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		GetCouponAllResponse actualResponse = memberCouponService.getMyCouponAllWithValid();

		assertEquals(expectedResponse, actualResponse);
		verify(memberCouponClient).getMemberCouponValidByMemberId();
	}

	@Test
	void getMyCouponHistoryAll_ShouldReturnPageOfResponse() {
		List<GetCouponHistoryResponse> expectedList = new ArrayList<>();
		PageResponse<GetCouponHistoryResponse> pageResponse = PageResponse.success(0, 10, expectedList.size(), expectedList);
		Page<GetCouponHistoryResponse> expectedPage = pageResponse.toPage();
		when(memberCouponClient.getMemberCouponHistoryByMemberId(anyInt(), anyInt())).thenReturn(pageResponse);

		Pageable pageable = PageRequest.of(0, 10);
		Page<GetCouponHistoryResponse> actualPage = memberCouponService.getMyCouponHistoryAll(pageable);

		assertEquals(expectedPage, actualPage);
		verify(memberCouponClient).getMemberCouponHistoryByMemberId(0, 10);
	}

	@Test
	void getMyUsedCouponHistory_ShouldReturnPageOfResponse() {
		List<GetUsedCouponHistoryResponse> expectedList = new ArrayList<>();
		PageResponse<GetUsedCouponHistoryResponse> pageResponse = PageResponse.success(0, 10, expectedList.size(), expectedList);
		Page<GetUsedCouponHistoryResponse> expectedPage = pageResponse.toPage();
		when(memberCouponClient.getMemberUsedCouponHistoryByMemberId(anyInt(), anyInt())).thenReturn(pageResponse);

		Pageable pageable = PageRequest.of(0, 10);
		Page<GetUsedCouponHistoryResponse> actualPage = memberCouponService.getMyUsedCouponHistory(pageable);

		assertEquals(expectedPage, actualPage);
		verify(memberCouponClient).getMemberUsedCouponHistoryByMemberId(0, 10);
	}

	@Test
	void downloadCoupon_ShouldInvokeClientMethod() {
		DownloadCouponRequest request = new DownloadCouponRequest(1L);

		memberCouponService.downloadCoupon(request);

		verify(memberCouponClient).downloadCoupon(request);
	}

	@Test
	void downloadLimitedCoupon_ShouldInvokeClientMethod() {
		DownloadCouponMessageRequest request = new DownloadCouponMessageRequest("coupon_code", CouponType.LIMITED, 1L);

		memberCouponService.downloadLimitedCoupon(request);

		verify(memberCouponClient).downloadLimitedCoupon(request);
	}
}

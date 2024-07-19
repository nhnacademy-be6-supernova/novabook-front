package store.novabook.front.store.mypage.mypage;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.coupon.domain.CouponStatus;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;
import store.novabook.front.api.coupon.dto.response.GetCouponHistoryResponse;
import store.novabook.front.api.member.grade.dto.GetMemberGradeResponse;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.member.member.service.MemberCouponService;
import store.novabook.front.api.order.service.OrdersBookService;
import store.novabook.front.api.point.dto.response.GetPointHistoryResponse;
import store.novabook.front.api.point.service.PointService;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.store.order.dto.GetOrdersBookResponse;

@ExtendWith(MockitoExtension.class)
class MypageControllerTest {

	@Mock
	private MemberGradeService memberGradeService;

	@Mock
	private OrdersBookService ordersBookService;

	@Mock
	private PointService pointService;

	@Mock
	private MemberCouponService memberCouponService;

	@InjectMocks
	private MypageController mypageController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(mypageController).build();
	}

	@Test
	void testGetMypage() throws Exception {
		int page = 0;
		int size = 2;
		Pageable pageable = PageRequest.of(page, size);

		GetPointHistoryResponse getPointHistoryResponse = GetPointHistoryResponse.builder()
			.pointContent("Points for purchase")
			.pointAmount(500L)
			.createdAt(LocalDateTime.now())
			.build();
		List<GetPointHistoryResponse> data = new ArrayList<>();
		data.add(getPointHistoryResponse);
		PageResponse<GetPointHistoryResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);

		GetOrdersBookResponse getOrdersBookResponse = new GetOrdersBookResponse(
			1L, // ordersId
			"The Pragmatic Programmer", // firstBookTitle
			2L, // extraBookCount
			5000L, // totalAmount
			"Shipped", // orderStatus
			LocalDateTime.now() // createdAt
		);
		List<GetOrdersBookResponse> data2 = new ArrayList<>();
		data2.add(getOrdersBookResponse);
		PageResponse<GetOrdersBookResponse> expectedResponse2 = new PageResponse<>(1, 10, 30, data2);

		GetCouponHistoryResponse getCouponHistoryResponse = GetCouponHistoryResponse.builder()
			.createdAt(LocalDateTime.now())
			.name("20% Off Books")
			.type(CouponType.GENERAL)
			.status(CouponStatus.UNUSED)
			.discountAmount(20)
			.discountType(DiscountType.AMOUNT)
			.build();

		// Mock data
		when(pointService.getPointHistories(page, size)).thenReturn(expectedResponse);
		when(ordersBookService.getOrdersBookAll(page, size)).thenReturn(expectedResponse2);
		when(memberCouponService.getMyCouponHistoryAll(pageable)).thenReturn(
			new PageImpl<>(Collections.singletonList(getCouponHistoryResponse)));
		when(memberGradeService.getMemberGrade()).thenReturn(new GetMemberGradeResponse("Gold"));

		// Perform GET request to "/mypage"
		mockMvc.perform(get("/mypage"))
			.andExpect(status().isOk()) // Expect HTTP 200 OK status
			.andExpect(view().name("store/mypage/mypage_index")) // Expect view name
			.andExpect(model().attributeExists("pointHistories")) // Expect "pointHistories" attribute in the model
			.andExpect(model().attributeExists("orders")) // Expect "orders" attribute in the model
			.andExpect(model().attributeExists("coupons")) // Expect "coupons" attribute in the model
			.andExpect(model().attributeExists("grade")) // Expect "grade" attribute in the model
			.andExpect(model().attribute("grade", new GetMemberGradeResponse("Gold")));

		// Verify that the service methods were called with correct arguments
		verify(pointService).getPointHistories(page, size);
		verify(ordersBookService).getOrdersBookAll(page, size);
		verify(memberCouponService).getMyCouponHistoryAll(pageable);
		verify(memberGradeService).getMemberGrade();
	}
}

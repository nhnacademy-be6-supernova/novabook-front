package store.novabook.front.api.member.member.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.member.member.service.GuestClient;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.store.order.dto.GetGuestOrderHistoryRequest;
import store.novabook.front.store.order.dto.GetOrderDetailResponse;

class GuestServiceImplTest {

	@InjectMocks
	private GuestServiceImpl guestService;

	@Mock
	private GuestClient guestClient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void login_ShouldReturnOrderDetailResponse() {
		// Arrange
		GetGuestOrderHistoryRequest request = new GetGuestOrderHistoryRequest("code", "010-1234-1234");
		GetOrderDetailResponse response = GetOrderDetailResponse.builder()
			.ordersId(12345L)
			.ordersStatusId(1L)
			.ordersStatusName("Shipped")
			.bookTitle(Arrays.asList("Book 1", "Book 2"))
			.quantity(2)
			.deliveryFee(5000L)
			.wrappingFee(2000L)
			.receiverName("John Doe")
			.receiverNumber("123-456-7890")
			.receiverAddress("123 Main St, Anytown, USA")
			.expectedDeliveryDate(LocalDateTime.now().plusDays(3))
			.totalPrice(50000L)
			.couponDiscountAmount(5000L)
			.finalAmount(45000L)
			.pointSaveAmount(1000L)
			.build();

		ApiResponse<GetOrderDetailResponse> apiResponse = new ApiResponse<>("SUCCESS", true, response);

		when(guestClient.getOrder(any(GetGuestOrderHistoryRequest.class))).thenReturn(apiResponse);

		GetOrderDetailResponse actualResponse = guestService.login(request);

		assertNotNull(actualResponse);
		assertEquals(response, actualResponse);
		verify(guestClient, times(1)).getOrder(any(GetGuestOrderHistoryRequest.class));
	}
}

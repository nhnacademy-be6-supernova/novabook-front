package store.novabook.front.api.order.service.impl;

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

import store.novabook.front.api.order.client.OrdersBookClient;
import store.novabook.front.api.order.dto.response.GetOrdersBookReviewIdResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.store.order.dto.GetOrderDetailResponse;
import store.novabook.front.store.order.dto.GetOrdersBookResponse;

public class OrdersBookServiceImplTest {

	@Mock
	private OrdersBookClient ordersBookClient;

	@InjectMocks
	private OrdersBookServiceImpl ordersBookService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetOrdersBookReviewId() {

		GetOrdersBookReviewIdResponse ordersBookReviewIdResponse = new GetOrdersBookReviewIdResponse(
			1L, // ordersBookId
			2L, // reviewId
			3L, // ordersId
			4L, // bookId
			"Effective Java", // bookTitle
			LocalDateTime.now() // orderAt
		);

		List<GetOrdersBookReviewIdResponse> data = new ArrayList<>();
		data.add(ordersBookReviewIdResponse);

		// Given
		int page = 0;
		int size = 10;
		PageResponse<GetOrdersBookReviewIdResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);
		when(ordersBookClient.getOrdersBookReviewId(page, size)).thenReturn(expectedResponse);

		// When
		PageResponse<GetOrdersBookReviewIdResponse> actualResponse = ordersBookService.getOrdersBookReviewId(page, size);

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(ordersBookClient, times(1)).getOrdersBookReviewId(page, size);
	}

	@Test
	void testGetOrdersBookAll() {

		GetOrdersBookResponse ordersBookResponse = new GetOrdersBookResponse(
			1L, // ordersId
			"The Pragmatic Programmer", // firstBookTitle
			2L, // extraBookCount
			50000L, // totalAmount
			"Shipped", // orderStatus
			LocalDateTime.of(2023, 4, 15, 10, 30) // createdAt
		);

		List<GetOrdersBookResponse> data = new ArrayList<>();
		data.add(ordersBookResponse);
		// Given
		int page = 0;
		int size = 10;
		PageResponse<GetOrdersBookResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);
		when(ordersBookClient.getOrdersBookAll(page, size)).thenReturn(expectedResponse);

		// When
		PageResponse<GetOrdersBookResponse> actualResponse = ordersBookService.getOrdersBookAll(page, size);

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(ordersBookClient, times(1)).getOrdersBookAll(page, size);
	}

	@Test
	void testGetOrderDetail() {

		GetOrderDetailResponse orderDetailResponse = GetOrderDetailResponse.builder()
			.ordersId(1L)
			.ordersStatusId(2L)
			.ordersStatusName("Processed")
			.bookTitle(List.of("Clean Code", "Effective Java"))
			.quantity(2)
			.deliveryFee(500L)
			.wrappingFee(200L)
			.receiverName("John Doe")
			.receiverNumber("010-1234-5678")
			.receiverAddress("1234 Main St, Anytown, AN 12345")
			.expectedDeliveryDate(LocalDateTime.of(2023, 5, 15, 10, 30))
			.totalPrice(10000L)
			.couponDiscountAmount(1000L)
			.finalAmount(9000L)
			.pointSaveAmount(450L)
			.build();
		// Given
		Long ordersId = 1L;
		when(ordersBookClient.getOrderDetails(ordersId)).thenReturn(new ApiResponse<>("SUCCESS", true, orderDetailResponse));

		// When
		GetOrderDetailResponse actualResponse = ordersBookService.getOrderDetail(ordersId);

		// Then
		assertEquals(orderDetailResponse, actualResponse);
		verify(ordersBookClient, times(1)).getOrderDetails(ordersId);
	}
}

package store.novabook.front.api.delivery.service.impl;

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

import store.novabook.front.api.delivery.client.DeliveryFeeClient;
import store.novabook.front.api.delivery.dto.request.CreateDeliveryFeeRequest;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.common.response.PageResponse;

class DeliveryFeeServiceImplTest {

	@Mock
	private DeliveryFeeClient deliveryFeeClient;

	@InjectMocks
	private DeliveryFeeServiceImpl deliveryFeeService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateDeliveryFee() {
		CreateDeliveryFeeRequest request = new CreateDeliveryFeeRequest(1000L);

		deliveryFeeService.createDeliveryFee(request);

		verify(deliveryFeeClient, times(1)).createDeliveryFee(request);
	}

	@Test
	void testGetDeliveryFeeAllPage() {

		GetDeliveryFeeResponse deliveryFeeResponse = GetDeliveryFeeResponse.builder()
			.id(1L)
			.fee(500L)
			.createdAt(LocalDateTime.of(2023, 4, 1, 0, 0))
			.updatedAt(LocalDateTime.of(2023, 4, 10, 0, 0))
			.build();
		List<GetDeliveryFeeResponse> data = new ArrayList<>();
		data.add(deliveryFeeResponse);

		int page = 0;
		int size = 10;
		PageResponse<GetDeliveryFeeResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);
		when(deliveryFeeClient.getDeliveryAllPage(page, size)).thenReturn(expectedResponse);

		PageResponse<GetDeliveryFeeResponse> actualResponse = deliveryFeeService.getDeliveryFeeAllPage(page, size);

		assertEquals(expectedResponse, actualResponse);
		verify(deliveryFeeClient, times(1)).getDeliveryAllPage(page, size);
	}
}

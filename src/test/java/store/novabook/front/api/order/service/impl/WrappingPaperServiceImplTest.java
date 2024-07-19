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

import store.novabook.front.api.order.client.WrappingPaperClient;
import store.novabook.front.api.order.dto.request.CreateWrappingPaperRequest;
import store.novabook.front.api.order.dto.request.UpdateWrappingPaperRequest;
import store.novabook.front.api.order.dto.response.GetWrappingPaperAllResponse;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

public class WrappingPaperServiceImplTest {

	@Mock
	private WrappingPaperClient wrappingPaperClient;

	@InjectMocks
	private WrappingPaperServiceImpl wrappingPaperService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetWrappingPaperAllList() {
		// Given
		List<GetWrappingPaperResponse> wrappingPaperList = new ArrayList<>();
		GetWrappingPaperAllResponse expectedResponse = new GetWrappingPaperAllResponse(wrappingPaperList);

		when(wrappingPaperClient.getWrappingPaperAllList()).thenReturn(
			new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		List<GetWrappingPaperResponse> actualResponse = wrappingPaperService.getWrappingPaperAllList();

		// Then
		assertEquals(wrappingPaperList, actualResponse);
		verify(wrappingPaperClient, times(1)).getWrappingPaperAllList();
	}

	@Test
	void testGetWrappingPaperAllPage() {

		GetWrappingPaperResponse wrappingPaperResponse = GetWrappingPaperResponse.builder()
			.id(1L)
			.price(1500L)
			.name("Festive Season")
			.status("Available")
			.createdAt(LocalDateTime.of(2023, 10, 1, 0, 0))
			.updatedAt(LocalDateTime.of(2023, 10, 5, 0, 0))
			.build();
		List<GetWrappingPaperResponse> data = new ArrayList<>();
		data.add(wrappingPaperResponse);
		// Given
		int page = 0;
		int size = 10;
		PageResponse<GetWrappingPaperResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);
		when(wrappingPaperClient.getWrappingPaperAllPage(page, size)).thenReturn(expectedResponse);

		// When
		PageResponse<GetWrappingPaperResponse> actualResponse = wrappingPaperService.getWrappingPaperAllPage(page,
			size);

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(wrappingPaperClient, times(1)).getWrappingPaperAllPage(page, size);
	}

	@Test
	void testGetWrappingPaper() {
		GetWrappingPaperResponse wrappingPaperResponse = GetWrappingPaperResponse.builder()
			.id(1L)
			.price(1500L)
			.name("Festive Season")
			.status("Available")
			.createdAt(LocalDateTime.of(2023, 10, 1, 0, 0))
			.updatedAt(LocalDateTime.of(2023, 10, 5, 0, 0))
			.build();
		// Given
		Long id = 1L;
		when(wrappingPaperClient.getWrappingPaper(id)).thenReturn(
			new ApiResponse<>("SUCCESS", true, wrappingPaperResponse));

		// When
		GetWrappingPaperResponse actualResponse = wrappingPaperService.getWrappingPaper(id);

		// Then
		assertEquals(wrappingPaperResponse, actualResponse);
		verify(wrappingPaperClient, times(1)).getWrappingPaper(id);
	}

	@Test
	void testCreateWrappingPaper() {

		CreateWrappingPaperRequest request = CreateWrappingPaperRequest.builder()
			.price(2000L)
			.name("Elegant Gold")
			.status("Available")
			.build();

		// When
		wrappingPaperService.createWrappingPaper(request);

		// Then
		verify(wrappingPaperClient, times(1)).createWrappingPaper(request);
	}

	@Test
	void testUpdateWrappingPaper() {
		// Given
		Long id = 1L;
		UpdateWrappingPaperRequest request = UpdateWrappingPaperRequest.builder()
			.price(2500L)
			.name("Luxury Shine")
			.status("In Stock")
			.build();

		// When
		wrappingPaperService.updateWrappingPaper(id, request);

		// Then
		verify(wrappingPaperClient, times(1)).putWrappingPaper(request, id);
	}
}

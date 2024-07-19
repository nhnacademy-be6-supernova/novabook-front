package store.novabook.front.api.point.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.point.dto.request.CreatePointPolicyRequest;
import store.novabook.front.api.point.dto.response.CreatePointPolicyResponse;
import store.novabook.front.api.point.dto.response.GetPointPolicyResponse;
import store.novabook.front.api.point.service.PointPolicyClient;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

class PointPolicyServiceImplTest {

	@Mock
	private PointPolicyClient pointPolicyClient;

	@InjectMocks
	private PointPolicyServiceImpl pointPolicyService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreatePointPolicy() {
		// Given
		CreatePointPolicyRequest request = CreatePointPolicyRequest.builder()
			.reviewPointRate(10L)
			.basicPoint(100L)
			.registerPoint(50L)
			.build();

		CreatePointPolicyResponse expectedResponse = new CreatePointPolicyResponse(1L);

		when(pointPolicyClient.createPointPolicy(request)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		pointPolicyService.createPointPolicy(request);

		// Then
		verify(pointPolicyClient, times(1)).createPointPolicy(request);
	}


	@Test
	void testGetPointPolicyAllPage() {

		GetPointPolicyResponse getPointPolicyResponse = GetPointPolicyResponse.builder()
			.reviewPointRate(20L)
			.basicPoint(200L)
			.registerPoint(100L)
			.build();

		List<GetPointPolicyResponse> data = new ArrayList<>();
		data.add(getPointPolicyResponse);
		// Given
		int page = 1;
		int size = 10;

		PageResponse<GetPointPolicyResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);
		when(pointPolicyClient.getPointPolicyAll(page, size)).thenReturn(expectedResponse);
		// new PageResponse<>(1, 10, 30, data);
		// When
		PageResponse<GetPointPolicyResponse> actualResponse = pointPolicyService.getPointPolicyAllPage(page, size);

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(pointPolicyClient, times(1)).getPointPolicyAll(page, size);
	}

	@Test
	void testGetLatestPointPolicy() {
		GetPointPolicyResponse expectedResponse = GetPointPolicyResponse.builder()
			.reviewPointRate(20L)
			.basicPoint(200L)
			.registerPoint(100L)
			.build();
		// Given
		when(pointPolicyClient.getLatestPoint()).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		GetPointPolicyResponse actualResponse = pointPolicyService.getLatestPointPolicy();

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(pointPolicyClient, times(1)).getLatestPoint();
	}
}

package store.novabook.front.api.point.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.point.dto.GetMemberPointResponse;
import store.novabook.front.api.point.dto.response.GetPointHistoryResponse;
import store.novabook.front.api.point.service.PointHistoryClient;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

class PointServiceImplTest {

	@Mock
	private PointHistoryClient pointHistoryClient;

	@InjectMocks
	private PointServiceImpl pointService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetMemberPoint() {
		// Given
		GetMemberPointResponse expectedResponse = new GetMemberPointResponse(1L);
		when(pointHistoryClient.getPointTotalByMemberId()).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		GetMemberPointResponse actualResponse = pointService.getMemberPoint();

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(pointHistoryClient, times(1)).getPointTotalByMemberId();
	}

	@Test
	void testGetPointHistories() {

		GetPointHistoryResponse getPointHistoryResponse = GetPointHistoryResponse.builder()
			.pointContent("Earned from purchase")
			.pointAmount(500L)
			.createdAt(LocalDateTime.now())
			.build();
		List<GetPointHistoryResponse> data = List.of(getPointHistoryResponse);
		// Given
		int page = 1;
		int size = 10;
		PageResponse<GetPointHistoryResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);
		when(pointHistoryClient.getPointHistoryByMemberIdPage(page, size)).thenReturn(expectedResponse);

		// When
		PageResponse<GetPointHistoryResponse> actualResponse = pointService.getPointHistories(page, size);

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(pointHistoryClient, times(1)).getPointHistoryByMemberIdPage(page, size);
	}
}

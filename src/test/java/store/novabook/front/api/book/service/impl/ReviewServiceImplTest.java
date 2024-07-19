package store.novabook.front.api.book.service.impl;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import store.novabook.front.api.book.dto.request.CreateReviewRequest;
import store.novabook.front.api.book.dto.request.UpdateReviewRequest;
import store.novabook.front.api.book.dto.response.GetReviewListResponse;
import store.novabook.front.api.book.dto.response.GetReviewResponse;
import store.novabook.front.api.book.service.ReviewClient;
import store.novabook.front.common.response.ApiResponse;

public class ReviewServiceImplTest {

	@Mock
	private ReviewClient reviewClient;

	@InjectMocks
	private ReviewServiceImpl reviewService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateReview() {
		// Given
		String content = "Great book!";
		int score = 5;
		Long ordersBookId = 1L;

		MockMultipartFile file3 = new MockMultipartFile("reviewImages", "test1.jpg", "image/jpeg", new byte[10]);
		MockMultipartFile file4 = new MockMultipartFile("reviewImages", "test2.jpg", "image/jpeg", new byte[20]);
		List<MultipartFile> reviewImages2 = new ArrayList<>();
		reviewImages2.add(file3);
		reviewImages2.add(file4);



		CreateReviewRequest expectedRequest = CreateReviewRequest.builder()
			.content(content)
			.score(score)
			.reviewImageDTOs(new ArrayList<>())
			.build();
		expectedRequest.setReviewImageDTOs(reviewImages2);



		// When
		reviewService.createReview(content, score, reviewImages2, ordersBookId);

		// Then
		verify(reviewClient, times(1)).createReview(expectedRequest, ordersBookId);
	}

	@Test
	void testGetReviewById() {
		// Given
		Long reviewId = 1L;
		GetReviewResponse expectedResponse = GetReviewResponse.builder()
			.nickName("User123")
			.reviewId(1L)
			.orderBookId(101L)
			.content("This book provides deep insights into Java programming. Highly recommended!")
			.createdAt(LocalDateTime.of(2023, 4, 15, 10, 30))
			.reviewImages(List.of("image1.jpg", "image2.jpg"))
			.score(5)
			.build();

		// Mocking the client's behavior
		when(reviewClient.getReviewByReviewId(reviewId)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		GetReviewResponse actualResponse = reviewService.getReviewById(reviewId);

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(reviewClient, times(1)).getReviewByReviewId(reviewId);
	}

	@Test
	void testGetReviewsByBookId() {
		// Given

		GetReviewResponse reviewResponse = GetReviewResponse.builder()
			.nickName("User123")
			.reviewId(1L)
			.orderBookId(101L)
			.content("This book provides deep insights into Java programming. Highly recommended!")
			.createdAt(LocalDateTime.of(2023, 4, 15, 10, 30))
			.reviewImages(List.of("image1.jpg", "image2.jpg"))
			.score(5)
			.build();
		Long bookId = 1L;
		GetReviewListResponse expectedResponse = new GetReviewListResponse(List.of(reviewResponse));

		// Mocking the client's behavior
		when(reviewClient.getReviewListByBookId(bookId)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		GetReviewListResponse actualResponse = reviewService.getReviewsByBookId(bookId);

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(reviewClient, times(1)).getReviewListByBookId(bookId);
	}

	@Test
	void testUpdateReview() {

		UpdateReviewRequest request = UpdateReviewRequest.builder()
			.bookId(1L)
			.content("Updated review content here.")
			.score(4)
			.build();
		// Given
		Long reviewId = 1L;

		// When
		reviewService.updateReview(request, reviewId);

		// Then
		verify(reviewClient, times(1)).updateReview(reviewId, request);
	}
}

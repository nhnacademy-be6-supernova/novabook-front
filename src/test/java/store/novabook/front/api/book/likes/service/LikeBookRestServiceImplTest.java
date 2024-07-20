package store.novabook.front.api.book.likes.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.book.likes.dto.LikeBookResponse;
import store.novabook.front.api.book.likes.service.impl.LikeBookRestServiceImpl;
import store.novabook.front.api.book.service.BookLikeClient;
import store.novabook.front.common.response.ApiResponse;

class LikeBookRestServiceImplTest {

	@Mock
	private BookLikeClient bookLikeClient;

	@InjectMocks
	private LikeBookRestServiceImpl likeBookRestService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetBookLikes() {
		Long bookId = 1L;
		LikeBookResponse expectedResponse = new LikeBookResponse(true);

		when(bookLikeClient.getBookLike(bookId)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		LikeBookResponse actualResponse = likeBookRestService.getBookLikes(bookId);

		assertEquals(expectedResponse, actualResponse);
		verify(bookLikeClient, times(1)).getBookLike(bookId);
	}

	@Test
	void testLikeButton() {
		Long bookId = 1L;
		LikeBookResponse expectedResponse = new LikeBookResponse(true);

		when(bookLikeClient.buttonLikes(bookId)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		LikeBookResponse actualResponse = likeBookRestService.likeButton(bookId);

		assertEquals(expectedResponse, actualResponse);
		verify(bookLikeClient, times(1)).buttonLikes(bookId);
	}
}

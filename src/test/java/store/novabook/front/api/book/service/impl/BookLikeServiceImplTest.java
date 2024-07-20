package store.novabook.front.api.book.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.book.dto.response.GetBookLikeResponse;
import store.novabook.front.api.book.service.BookLikeClient;
import store.novabook.front.common.response.PageResponse;

class BookLikeServiceImplTest {

	@Mock
	private BookLikeClient bookLikeClient;

	@InjectMocks
	private BookLikeServiceImpl bookLikeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetBookLikeAllPage() {
		int page = 1;
		int size = 10;
		PageResponse<GetBookLikeResponse> expectedResponse = createMockPageResponse();

		when(bookLikeClient.getBookLikeAllPage(page, size)).thenReturn(expectedResponse);

		PageResponse<GetBookLikeResponse> actualResponse = bookLikeService.getBookLikeAllPage(page, size);

		assertEquals(expectedResponse, actualResponse);
		verify(bookLikeClient, times(1)).getBookLikeAllPage(page, size);
	}

	private PageResponse<GetBookLikeResponse> createMockPageResponse() {
		return new PageResponse<>(1, 10, 100, createMockData());
	}

	private List<GetBookLikeResponse> createMockData() {
		List<GetBookLikeResponse> data = new ArrayList<>();
		data.add(new GetBookLikeResponse(1L, 1L, "Book A", "a", "a"));
		data.add(new GetBookLikeResponse(2L, 2L,"Book B", "b", "b"));
		data.add(new GetBookLikeResponse(3L, 3L, "Book C", "c", "c"));
		return data;
	}
}

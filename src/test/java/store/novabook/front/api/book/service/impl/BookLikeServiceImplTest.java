package store.novabook.front.api.book.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import store.novabook.front.api.book.dto.response.GetBookLikeResponse;
import store.novabook.front.api.book.service.BookLikeClient;
import store.novabook.front.common.response.PageResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class BookLikeServiceImplTest {

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
		// Given
		int page = 1;
		int size = 10;
		PageResponse<GetBookLikeResponse> expectedResponse = createMockPageResponse();

		// Mocking the client's behavior
		when(bookLikeClient.getBookLikeAllPage(page, size)).thenReturn(expectedResponse);

		// When
		PageResponse<GetBookLikeResponse> actualResponse = bookLikeService.getBookLikeAllPage(page, size);

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(bookLikeClient, times(1)).getBookLikeAllPage(page, size);
	}

	private PageResponse<GetBookLikeResponse> createMockPageResponse() {
		// This method creates a mock PageResponse<GetBookLikeResponse> for testing purposes
		// You can customize this method to create different mock responses as needed
		return new PageResponse<>(1, 10, 100, createMockData());
	}

	private List<GetBookLikeResponse> createMockData() {
		// This method creates mock data of GetBookLikeResponse objects for testing purposes
		// You can customize this method to create different mock data as needed
		List<GetBookLikeResponse> data = new ArrayList<>();
		data.add(new GetBookLikeResponse(1L, 1L, "Book A", "a", "a"));
		data.add(new GetBookLikeResponse(2L, 2L,"Book B", "b", "b"));
		data.add(new GetBookLikeResponse(3L, 3L, "Book C", "c", "c"));
		return data;
	}
}

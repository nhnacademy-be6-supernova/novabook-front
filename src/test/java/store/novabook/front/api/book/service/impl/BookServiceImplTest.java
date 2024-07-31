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

import store.novabook.front.api.book.dto.request.CreateBookRequest;
import store.novabook.front.api.book.dto.request.UpdateBookRequest;
import store.novabook.front.api.book.dto.response.GetBookAllResponse;
import store.novabook.front.api.book.dto.response.GetBookResponse;
import store.novabook.front.api.book.dto.response.GetBookSearchResponse;
import store.novabook.front.api.book.service.BookClient;
import store.novabook.front.api.book.service.BookSearchClient;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

class BookServiceImplTest {

	@Mock
	private BookClient bookClient;

	@Mock
	private BookSearchClient bookSearchClient;

	@InjectMocks
	private BookServiceImpl bookService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetBookClient() {
		// Given
		Long id = 1L;
		GetBookResponse expectedResponse = GetBookResponse.builder()
			.id(1L)
			.isbn("978-3-16-148410-0")
			.title("Java Design Patterns")
			.description("A comprehensive guide to design patterns in Java")
			.descriptionDetail("This book covers the most common design patterns and their implementation in Java. It provides a deep understanding of how patterns work together and how they can be applied in real-world software development.")
			.author("John Doe")
			.publisher("Tech Press")
			.publicationDate(LocalDateTime.of(2022, 10, 15, 0, 0))
			.inventory(100)
			.price(60000L)
			.discountPrice(54000L)
			.isPackaged(true)
			.tags(List.of("Java", "Design Patterns", "Software Engineering"))
			.categories(List.of("Programming", "Software Design"))
			.likes(150)
			.score(4.5)
			.image("java_design_patterns_cover.jpg")
			.bookStatusId(2L)
			.build();

		when(bookClient.getBook(id)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		GetBookResponse actualResponse = bookService.getBookClient(id);

		assertEquals(expectedResponse, actualResponse);
		verify(bookClient, times(1)).getBook(id);
	}

	@Test
	void testCreateBook() {
		CreateBookRequest createBookRequest = new CreateBookRequest(
			1L,
			"978-3-16-148410-0",
			"The Art of Computer Programming",
			"A comprehensive computer programming book covering various aspects of algorithm analysis and complexity.",
			"This book is considered one of the best works in the field of computer science, providing a wide range of algorithms and discussing their analysis.",
			"Donald E. Knuth",
			"Addison-Wesley",
			LocalDateTime.of(2023, 10, 1, 0, 0),
			100,
			50000L,
			45000L,
			true,
			List.of(1L, 2L),
			List.of(3L, 4L),
			"the_art_of_computer_programming_cover.jpg"
		);

		bookService.createBook(createBookRequest);

		verify(bookClient, times(1)).createBook(createBookRequest);
	}

	@Test
	void testGetBookAll() {
		int page = 1;
		int size = 10;
		PageResponse<GetBookAllResponse> expectedResponse = createMockPageResponse();

		when(bookClient.getBookAll(page, size)).thenReturn(expectedResponse);

		PageResponse<GetBookAllResponse> actualResponse = bookService.getBookAll(page, size);

		assertEquals(expectedResponse, actualResponse);
		verify(bookClient, times(1)).getBookAll(page, size);
	}

	@Test
	void testUpdateBook() {
		UpdateBookRequest updateBookRequest = UpdateBookRequest.builder()
			.id(1L)
			.bookStatusId(2L)
			.inventory(100)
			.price(60000L)
			.discountPrice(54000L)
			.isPackaged(true)
			.build();

		bookService.updateBook(updateBookRequest);

		verify(bookClient, times(1)).updateBook(updateBookRequest);
	}

	@Test
	void testGetBookSearchAllPage() {
		String keyword = "Java";
		int page = 1;
		int size = 10;
		String sort = "title";

		PageResponse<GetBookSearchResponse> expectedResponse = createMockSearchPageResponse();

		when(bookSearchClient.searchByKeyword(keyword, page, size, sort)).thenReturn(expectedResponse);

		PageResponse<GetBookSearchResponse> actualResponse = bookService.getBookSearchAllPage(keyword, page, size, sort);

		assertEquals(expectedResponse, actualResponse);
		verify(bookSearchClient, times(1)).searchByKeyword(keyword, page, size, sort);
	}

	@Test
	void testGetBookSearchCategory() {
		String category = "Programming";
		int page = 1;
		int size = 10;
		String sort = "title";

		PageResponse<GetBookSearchResponse> expectedResponse = createMockSearchPageResponse();

		when(bookSearchClient.searchByCategory(category, page, size, sort)).thenReturn(expectedResponse);

		PageResponse<GetBookSearchResponse> actualResponse = bookService.getBookSearchCategory(category, page, size, sort);

		assertEquals(expectedResponse, actualResponse);
		verify(bookSearchClient, times(1)).searchByCategory(category, page, size, sort);
	}

	private PageResponse<GetBookAllResponse> createMockPageResponse() {
		List<GetBookAllResponse> data = new ArrayList<>();
		data.add( GetBookAllResponse.builder()
			.id(1L)
			.bookStatusId(2L)
			.title("Effective Java")
			.inventory(50)
			.price(45000L)
			.discountPrice(40500L)
			.isPackaged(true)
			.build());
		data.add( GetBookAllResponse.builder()
			.id(2L)
			.bookStatusId(3L)
			.title("Effective test")
			.inventory(10)
			.price(35000L)
			.discountPrice(20500L)
			.isPackaged(true)
			.build());
		data.add( GetBookAllResponse.builder()
			.id(3L)
			.bookStatusId(4L)
			.title("Effective all")
			.inventory(70)
			.price(7000L)
			.discountPrice(60500L)
			.isPackaged(true)
			.build());
		return new PageResponse<>(1, 10, 30, data);
	}

	private PageResponse<GetBookSearchResponse> createMockSearchPageResponse() {
		List<GetBookSearchResponse> data = new ArrayList<>();
		data.add(GetBookSearchResponse.builder()
			.id(1L)
			.title("Java Concurrency in Practice")
			.author("Brian Goetz")
			.publisher("Addison-Wesley")
			.publication_date(LocalDateTime.of(2006, 5, 9, 0, 0))
			.image("java_concurrency_in_practice_cover.jpg")
			.price(55000L)
			.discountPrice(49500L)
			.score(5.0)
			.isPackaged(true)
			.build());
		data.add(GetBookSearchResponse.builder()
			.id(2L)
			.title("Effective Java")
			.author("Joshua Bloch")
			.publisher("Addison-Wesley")
			.publication_date(LocalDateTime.of(2008, 5, 28, 0, 0))
			.image("effective_java_cover.jpg")
			.price(45000L)
			.discountPrice(40500L)
			.score(5.0)
			.isPackaged(false)
			.build());

		data.add(GetBookSearchResponse.builder()
			.id(3L)
			.title("Clean Code: A Handbook of Agile Software Craftsmanship")
			.author("Robert C. Martin")
			.publisher("Prentice Hall")
			.publication_date(LocalDateTime.of(2008, 8, 11, 0, 0))
			.image("clean_code_cover.jpg")
			.price(38000L)
			.discountPrice(34200L)
			.score(5.0)
			.isPackaged(true)
			.build());
		return new PageResponse<>(1, 10, 30, data);
	}
}

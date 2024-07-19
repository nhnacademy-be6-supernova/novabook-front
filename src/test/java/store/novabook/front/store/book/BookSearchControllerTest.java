package store.novabook.front.store.book;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.book.dto.response.GetBookSearchResponse;
import store.novabook.front.api.book.service.BookService;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.ForbiddenException;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@WebMvcTest(BookSearchController.class)
public class BookSearchControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new BookSearchController(bookService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void testSearchKeyword() throws Exception {
		String keyword = "Spring Boot";
		int page = 0;
		int size = 20;
		String sort = "createdAt";

		// Mocking bookService response for search by keyword

		GetBookSearchResponse getBookSearchResponse = GetBookSearchResponse.builder()
			.id(1L)
			.title("Spring Boot in Action")
			.author("Craig Walls")
			.publisher("Manning Publications")
			.publication_date(LocalDateTime.of(2016, 4, 1, 0, 0))
			.image("https://example.com/spring-boot-in-action.jpg")
			.price(4000L)
			.discountPrice(3600L)
			.score(5)
			.isPackaged(false)
			.build();
		List<GetBookSearchResponse> data = List.of(getBookSearchResponse);

		PageResponse<GetBookSearchResponse> mockResponse = new PageResponse<>(1, 10, 30, data);
		when(bookService.getBookSearchAllPage(eq(keyword), anyInt(), anyInt(), eq(sort))).thenReturn(mockResponse);

		// Perform GET request to "/search/keyword" endpoint and validate the response
		mockMvc.perform(get("/search/keyword")
				.param("keyword", keyword)
				.param("page", String.valueOf(page))
				.param("size", String.valueOf(size))
				.param("sort", sort))
			.andExpect(status().isOk()) // Expect HTTP 200 OK status
			.andExpect(view().name("store/book/book_list")) // Expect view name to be "store/book/book_list"
			.andExpect(model().attributeExists("bookSearches")) // Expect "bookSearches" attribute in the model
			.andExpect(model().attribute("searchType", "keyword")) // Verify value of "searchType" attribute
			.andExpect(model().attribute("keyword", keyword)); // Verify value of "keyword" attribute
	}

	@Test
	void testSearchCategory() throws Exception {
		String category = "Programming";
		int page = 0;
		int size = 20;
		String sort = "createdAt";

		GetBookSearchResponse getBookSearchResponse = GetBookSearchResponse.builder()
			.id(2L)
			.title("Clean Code: A Handbook of Agile Software Craftsmanship")
			.author("Robert C. Martin")
			.publisher("Prentice Hall")
			.publication_date(LocalDateTime.of(2008, 8, 1, 0, 0))
			.image("https://example.com/clean-code.jpg")
			.price(4500L)
			.discountPrice(4050L)
			.score(5)
			.isPackaged(false)
			.build();
		List<GetBookSearchResponse> data = List.of(getBookSearchResponse);

		// Mocking bookService response for search by category
		PageResponse<GetBookSearchResponse> mockResponse = new PageResponse<>(1, 10, 30, data);
		when(bookService.getBookSearchCategory(eq(category), anyInt(), anyInt(), eq(sort))).thenReturn(mockResponse);

		// Perform GET request to "/search/category" endpoint and validate the response
		mockMvc.perform(get("/search/category")
				.param("category", category)
				.param("page", String.valueOf(page))
				.param("size", String.valueOf(size))
				.param("sort", sort))
			.andExpect(status().isOk()) // Expect HTTP 200 OK status
			.andExpect(view().name("store/book/book_list")) // Expect view name to be "store/book/book_list"
			.andExpect(model().attributeExists("bookSearches")) // Expect "bookSearches" attribute in the model
			.andExpect(model().attribute("searchType", "category")) // Verify value of "searchType" attribute
			.andExpect(model().attribute("keyword", category)); // Verify value of "keyword" attribute
	}

	@Test
	void testSearchKeywordNotFound() throws Exception {
		String keyword = "Nonexistent Keyword";

		// Mocking bookService to throw NovaException
		when(bookService.getBookSearchAllPage(anyString(), anyInt(), anyInt(), anyString()))
			.thenThrow(new ForbiddenException(ErrorCode.FORBIDDEN));

		// Perform GET request to "/search/keyword" endpoint and validate the response for not found scenario
		mockMvc.perform(get("/search/keyword")
				.param("keyword", keyword))
			.andExpect(status().isOk()) // Expect HTTP 200 OK status (as error handling is within the controller)
			.andExpect(view().name("error/404")); // Expect view name to be "error/404"
	}

	@Test
	void testSearchCategoryNotFound() throws Exception {
		String category = "Nonexistent Category";

		// Mocking bookService to throw NovaException
		when(bookService.getBookSearchCategory(anyString(), anyInt(), anyInt(), anyString()))
			.thenThrow(new ForbiddenException(ErrorCode.FORBIDDEN));

		// Perform GET request to "/search/category" endpoint and validate the response for not found scenario
		mockMvc.perform(get("/search/category")
				.param("category", category))
			.andExpect(status().isOk()) // Expect HTTP 200 OK status (as error handling is within the controller)
			.andExpect(view().name("error/404")); // Expect view name to be "error/404"
	}
}

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

import jakarta.servlet.http.Cookie;
import store.novabook.front.api.book.dto.response.GetBookResponse;
import store.novabook.front.api.book.dto.response.GetReviewListResponse;
import store.novabook.front.api.book.dto.response.GetReviewResponse;
import store.novabook.front.api.book.likes.dto.LikeBookResponse;
import store.novabook.front.api.book.likes.service.LikeBookRestService;
import store.novabook.front.api.book.service.BookService;
import store.novabook.front.api.book.service.ReviewService;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.ForbiddenException;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;
import store.novabook.front.common.security.aop.dto.GetMembersTokenResponse;

@WebMvcTest(BookController.class)
class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReviewService reviewService;

	@MockBean
	private BookService bookService;

	@MockBean
	private LikeBookRestService likeBookRestService;

	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new BookController(reviewService, bookService, likeBookRestService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void testGetBook() throws Exception {
		Long bookId = 1L;
		Long memberId = 100L;
		GetBookResponse getBookResponse = GetBookResponse.builder()
			.id(1L)
			.isbn("978-3-16-148410-0")
			.title("Effective Java")
			.description("A comprehensive guide to programming in Java.")
			.descriptionDetail("This book covers best practices and design patterns.")
			.author("Joshua Bloch")
			.publisher("Addison-Wesley")
			.publicationDate(LocalDateTime.of(2018, 5, 8, 0, 0))
			.inventory(100)
			.price(4500L)
			.discountPrice(4000L)
			.isPackaged(false)
			.tags(List.of("Java", "Programming", "Best Practices"))
			.categories(List.of("Computer Science", "Programming Languages"))
			.likes(250)
			.score(4.8)
			.image("https://example.com/effective-java.jpg")
			.bookStatusId(2L)
			.build();

		when(bookService.getBookClient(anyLong())).thenReturn(getBookResponse);

		GetReviewResponse mockReviewResponse = GetReviewResponse.builder()
			.nickName("User123")
			.reviewId(1L)
			.orderBookId(10L)
			.content("Great book, highly recommended!")
			.createdAt(LocalDateTime.now())
			.reviewImages(List.of("https://example.com/review1.jpg", "https://example.com/review2.jpg"))
			.score(5)
			.build();

		GetReviewListResponse mockReviewListResponse = GetReviewListResponse.builder()
			.getReviewResponses(List.of(mockReviewResponse))
			.build();

		when(reviewService.getReviewsByBookId(anyLong())).thenReturn(mockReviewListResponse);

		ApiResponse<GetMembersTokenResponse> apiResponse = new ApiResponse<>("SUCCESS", true,
			new GetMembersTokenResponse(1L));
		when(memberAuthClient.token()).thenReturn(apiResponse);

		LikeBookResponse mockLikeResponse = new LikeBookResponse(false);
		when(likeBookRestService.getBookLikes(anyLong())).thenReturn(mockLikeResponse);

		mockMvc.perform(get("/books/book/{bookId}", bookId).cookie(new Cookie("Authorization", "token"))
				.param("memberId", String.valueOf(memberId)))
			.andExpect(status().isOk())
			.andExpect(view().name("store/book/book_detail"))
			.andExpect(model().attributeExists("book"))
			.andExpect(model().attributeExists("reviews"))
			.andExpect(model().attributeExists("isLiked"))
			.andExpect(model().attribute("isLiked", mockLikeResponse.isLiked()));
	}

	@Test
	void testGetBookNotFound() throws Exception {
		Long bookId = 1L;

		when(bookService.getBookClient(anyLong())).thenThrow(new ForbiddenException(ErrorCode.FORBIDDEN));

		mockMvc.perform(get("/books/book/{bookId}", bookId))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}
}

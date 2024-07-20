package store.novabook.front.store.mypage.review;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.book.dto.request.UpdateReviewRequest;
import store.novabook.front.api.book.dto.response.GetReviewResponse;
import store.novabook.front.api.book.service.ReviewService;
import store.novabook.front.api.member.grade.dto.GetMemberGradeResponse;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.order.dto.response.GetOrdersBookReviewIdResponse;
import store.novabook.front.api.order.service.OrdersBookService;
import store.novabook.front.common.response.PageResponse;

@ExtendWith(MockitoExtension.class)
class MypageReviewControllerTest {

	@Mock
	private MemberGradeService memberGradeService;

	@Mock
	private OrdersBookService ordersBookService;

	@Mock
	private ReviewService reviewService;

	@InjectMocks
	private MypageReviewController mypageReviewController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(mypageReviewController).build();
	}

	@Test
	void testGetReviewAll() throws Exception {

		GetOrdersBookReviewIdResponse getOrdersBookReviewIdResponse = new GetOrdersBookReviewIdResponse(
			1L,
			2L,
			3L,
			4L,
			"Clean Architecture",
			LocalDateTime.now()
		);
		List<GetOrdersBookReviewIdResponse> data = new ArrayList<>();
		data.add(getOrdersBookReviewIdResponse);
		PageResponse<GetOrdersBookReviewIdResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);


		when(ordersBookService.getOrdersBookReviewId(0, 10)).thenReturn(expectedResponse);
		when(memberGradeService.getMemberGrade()).thenReturn(new GetMemberGradeResponse("Gold"));

		mockMvc.perform(get("/mypage/reviews")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/mypage/review/review_list"))
			.andExpect(model().attributeExists("grade"))
			.andExpect(model().attributeExists("ordersBook"));
	}

	@Test
	void testWriteReview() throws Exception {
		Long ordersBookId = 1L;

		mockMvc.perform(get("/mypage/reviews/{ordersBookId}", ordersBookId))
			.andExpect(status().isOk())
			.andExpect(view().name("store/mypage/review/review_write"))
			.andExpect(model().attributeExists("ordersBookId"));
	}

	@Test
	void testGetUpdateReview() throws Exception {

		GetReviewResponse getReviewResponse = GetReviewResponse.builder()
			.nickName("User123")
			.reviewId(1L)
			.orderBookId(2L)
			.content("This book provides deep insights into Java programming.")
			.createdAt(LocalDateTime.now())
			.reviewImages(List.of("image1.jpg", "image2.jpg"))
			.score(5)
			.build();
		Long reviewId = 1L;
		when(reviewService.getReviewById(reviewId)).thenReturn(getReviewResponse);

		mockMvc.perform(get("/mypage/reviews/{reviewId}/update", reviewId))
			.andExpect(status().isOk())
			.andExpect(view().name("store/mypage/review/review_modify"))
			.andExpect(model().attributeExists("review"));
	}

	@Test
	void testCreateReview() throws Exception {
		Long ordersBookId = 1L;
		String content = "Great book!";
		int score = 5;
		MockMultipartFile reviewImage = new MockMultipartFile("reviewImages", "image.jpg", "image/jpeg", new byte[0]);

		mockMvc.perform(multipart("/mypage/reviews/{ordersBookId}", ordersBookId)
				.file(reviewImage)
				.param("content", content)
				.param("score", String.valueOf(score)))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/mypage/reviews"));

		verify(reviewService).createReview(content, score, List.of(reviewImage), ordersBookId);
	}

	@Test
	void testPostUpdateReview() throws Exception {
		Long reviewId = 1L;
		String content = "Updated review content";
		int score = 4;
		UpdateReviewRequest request = new UpdateReviewRequest(reviewId, content, score);

		mockMvc.perform(post("/mypage/reviews/{reviewId}/update", reviewId)
				.param("content", content)
				.param("score", String.valueOf(score)))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/mypage/reviews"));

		verify(reviewService).updateReview(request, reviewId);
	}
}

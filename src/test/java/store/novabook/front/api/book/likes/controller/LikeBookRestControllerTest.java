package store.novabook.front.api.book.likes.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.book.likes.dto.LikeBookResponse;
import store.novabook.front.api.book.likes.service.LikeBookRestService;

class LikeBookRestControllerTest {

	private MockMvc mockMvc;

	@Mock
	private LikeBookRestService likeBookRestService;

	@InjectMocks
	private LikeBookRestController likeBookRestController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(likeBookRestController).build();
	}

	@Test
	void testIsLiked() throws Exception {
		Long bookId = 1L;
		LikeBookResponse expectedResponse = new LikeBookResponse(true);

		when(likeBookRestService.likeButton(bookId)).thenReturn(expectedResponse);

		mockMvc.perform(post("/api/v1/front/books/likes/{bookId}", bookId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.isLiked").value(expectedResponse.isLiked()));

		verify(likeBookRestService, times(1)).likeButton(bookId);
	}
}

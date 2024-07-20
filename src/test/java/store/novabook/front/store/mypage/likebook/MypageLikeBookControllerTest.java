package store.novabook.front.store.mypage.likebook;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.book.dto.response.GetBookLikeResponse;
import store.novabook.front.api.book.service.LikeService;
import store.novabook.front.api.member.grade.dto.GetMemberGradeResponse;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.common.response.PageResponse;

@ExtendWith(MockitoExtension.class)
class MypageLikeBookControllerTest {

	@Mock
	private LikeService likeService;

	@Mock
	private MemberGradeService memberGradeService;

	@InjectMocks
	private MypageLikeBookController mypageLikeBookController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(mypageLikeBookController).build();
	}

	@Test
	void testGetLikeBookAll() throws Exception {
		int page = 0;
		int size = 10;
		GetBookLikeResponse getBookLikeResponse = GetBookLikeResponse.builder()
			.id(1L)
			.bookId(101L)
			.title("Effective Java")
			.author("Joshua Bloch")
			.publisher("Addison-Wesley")
			.build();

		List<GetBookLikeResponse> data = new ArrayList<>();
		data.add(getBookLikeResponse);

		PageResponse<GetBookLikeResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);

		when(likeService.getBookLikeAllPage(page, size)).thenReturn(expectedResponse);
		when(memberGradeService.getMemberGrade()).thenReturn(new GetMemberGradeResponse("Gold"));

		mockMvc.perform(get("/mypage/like")
				.param("page", String.valueOf(page))
				.param("size", String.valueOf(size)))
			.andExpect(status().isOk())
			.andExpect(view().name("store/mypage/likebook/like_book_list"))
			.andExpect(model().attributeExists("grade"))
			.andExpect(model().attributeExists("LikeBooks"))
			.andExpect(model().attribute("grade", new GetMemberGradeResponse("Gold")))
			.andExpect(model().attribute("LikeBooks", expectedResponse));

		verify(likeService).getBookLikeAllPage(page, size);
		verify(memberGradeService).getMemberGrade();
	}
}

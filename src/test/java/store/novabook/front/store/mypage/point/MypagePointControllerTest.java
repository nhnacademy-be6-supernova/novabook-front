package store.novabook.front.store.mypage.point;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.member.grade.dto.GetMemberGradeResponse;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.point.dto.GetMemberPointResponse;
import store.novabook.front.api.point.dto.response.GetPointHistoryResponse;
import store.novabook.front.api.point.service.PointService;
import store.novabook.front.common.response.PageResponse;

@ExtendWith(MockitoExtension.class)
class MypagePointControllerTest {

	@Mock
	private MemberGradeService memberGradeService;

	@Mock
	private PointService pointService;

	@InjectMocks
	private MypagePointController mypagePointController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(mypagePointController).build();
	}

	@Test
	void testGetPointAll() throws Exception {
		GetPointHistoryResponse getPointHistoryResponse = GetPointHistoryResponse.builder()
			.pointContent("Points for purchase")
			.pointAmount(500L)
			.createdAt(LocalDateTime.now())
			.build();

		List<GetPointHistoryResponse> data = new ArrayList<>();
		data.add(getPointHistoryResponse);
		PageResponse<GetPointHistoryResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);


		when(memberGradeService.getMemberGrade()).thenReturn(new GetMemberGradeResponse("Gold"));
		when(pointService.getMemberPoint()).thenReturn(new GetMemberPointResponse(1000L));
		when(pointService.getPointHistories(0, 10)).thenReturn(expectedResponse);

		mockMvc.perform(get("/mypage/points")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/mypage/point/point_list"))
			.andExpect(model().attributeExists("grade"))
			.andExpect(model().attributeExists("point"))
			.andExpect(model().attributeExists("pointHistories"));
	}
}

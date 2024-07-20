package store.novabook.front.store.mypage.refund;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

@ExtendWith(MockitoExtension.class)
class MypageRefundControllerTest {

	@Mock
	private MemberGradeService memberGradeService;

	@InjectMocks
	private MypageRefundController mypageRefundController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(mypageRefundController).build();
	}

	@Test
	void testGetRefundAll() throws Exception {
		when(memberGradeService.getMemberGrade()).thenReturn(new GetMemberGradeResponse("Gold"));

		mockMvc.perform(get("/mypage/refunds"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/mypage/refund/refund_list"))
			.andExpect(model().attributeExists("grade"));
	}
}

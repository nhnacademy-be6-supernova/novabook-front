package store.novabook.front.store.mypage.withdraw;

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

import store.novabook.front.api.member.member.dto.response.GetMemberResponse;
import store.novabook.front.api.member.member.service.MemberService;

@ExtendWith(MockitoExtension.class)
class MemberWithdrawControllerTest {

	@Mock
	private MemberService memberService;

	@InjectMocks
	private MemberWithdrawController memberWithdrawController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(memberWithdrawController).build();
	}

	@Test
	void testMemberWithdraw() throws Exception {
		GetMemberResponse getMemberResponse = new GetMemberResponse(
			1L, // id
			"userLoginId", // loginId
			1980, // birthYear
			7, // birthMonth
			14, // birthDay
			"010-1234-5678", // number
			"Jane Doe", // name
			"jane.doe@example.com" // email
		);
		when(memberService.getMemberById()).thenReturn(getMemberResponse);

		mockMvc.perform(get("/mypage/withdraw"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/mypage/withdraw/withdraw"))
			.andExpect(model().attributeExists("member"))
			.andExpect(model().attribute("member", getMemberResponse));

		verify(memberService).getMemberById();
	}

	@Test
	void testMemberUpdateToWithdraw() throws Exception {
		String reason = "I don't need the service anymore";

		mockMvc.perform(post("/mypage/withdraw/withdraw")
				.param("reason", reason)
				.contentType("application/x-www-form-urlencoded"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));

	}
}

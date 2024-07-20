package store.novabook.front.store.mypage.information;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.member.grade.dto.GetMemberGradeResponse;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.member.member.dto.response.GetMemberResponse;
import store.novabook.front.api.member.member.service.MemberService;

@ExtendWith(MockitoExtension.class)
class MyInformationControllerTest {

	@Mock
	private MemberService memberService;

	@Mock
	private MemberGradeService memberGradeService;

	@InjectMocks
	private MyInformationController myInformationController;

	private MockMvc mockMvc;


	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(myInformationController).build();
	}

	@Test
	void testGetMyInformation() throws Exception {
		GetMemberGradeResponse grade = new GetMemberGradeResponse("Gold");
		GetMemberResponse getMemberResponse = new GetMemberResponse(
			1L,
			"userLoginId",
			1980,
			12,
			25,
			"010-1234-5678",
			"John Doe",
			"johndoe@example.com"
		);
		when(memberService.getMemberById()).thenReturn(getMemberResponse);
		when(memberGradeService.getMemberGrade()).thenReturn(grade);

		mockMvc.perform(get("/mypage/information"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/mypage/information/my_information"))
			.andExpect(model().attributeExists("grade"))
			.andExpect(model().attribute("grade", grade))
			.andExpect(model().attributeExists("member"))
			.andExpect(model().attribute("member", getMemberResponse));
	}

	@Test
	void testUpdateMember() throws Exception {
		String name = "김김김";
		String number = "12345678901";

		mockMvc.perform(post("/mypage/information/update")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", name)
				.param("number", number))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/mypage/information"));

		verify(memberService).updateMember(argThat(request ->
			request.name().equals(name) && request.number().equals(number)));
	}

	@Test
	void testUpdateMemberPassword() throws Exception {
		String loginPassword = "!@#123Password";
		String loginPasswordConfirm = "!@#123Password";

		mockMvc.perform(post("/mypage/information/password")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("loginPassword", loginPassword)
				.param("loginPasswordConfirm", loginPasswordConfirm))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/mypage/information"));

		verify(memberService).updateMemberPassword(argThat(request ->
			request.loginPassword().equals(loginPassword) && request.loginPasswordConfirm().equals(loginPasswordConfirm)));
	}
}

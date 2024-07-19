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

import com.fasterxml.jackson.databind.ObjectMapper;

import store.novabook.front.api.member.grade.dto.GetMemberGradeResponse;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.member.member.dto.request.UpdateMemberRequest;
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

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(myInformationController).build();
	}

	@Test
	void testGetMyInformation() throws Exception {
		// Mock data
		UpdateMemberRequest member = new UpdateMemberRequest("John Doe", "johndoe@example.com");
		GetMemberGradeResponse grade = new GetMemberGradeResponse("Gold");
		GetMemberResponse getMemberResponse = new GetMemberResponse(
			1L, // id
			"userLoginId", // loginId
			1980, // birthYear
			12, // birthMonth
			25, // birthDay
			"010-1234-5678", // number
			"John Doe", // name
			"johndoe@example.com" // email
		);
		// Mock service methods
		when(memberService.getMemberById()).thenReturn(getMemberResponse);
		when(memberGradeService.getMemberGrade()).thenReturn(grade);

		// Perform GET request to "/mypage/information"
		mockMvc.perform(get("/mypage/information"))
			.andExpect(status().isOk()) // Expect HTTP 200 OK status
			.andExpect(view().name("store/mypage/information/my_information")) // Expect view name
			.andExpect(model().attributeExists("grade")) // Expect "grade" attribute in the model
			.andExpect(model().attribute("grade", grade)) // Expect correct member grade in the model
			.andExpect(model().attributeExists("member")) // Expect "member" attribute in the model
			.andExpect(model().attribute("member", getMemberResponse)); // Expect correct member information in the model
	}

	@Test
	void testUpdateMember() throws Exception {
		// Mock data
		String name = "김김김";
		String number = "12345678901";

		// Perform POST request to "/mypage/information/update"
		mockMvc.perform(post("/mypage/information/update")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", name)
				.param("number", number))
			.andExpect(status().is3xxRedirection()) // Expect HTTP redirect status
			.andExpect(redirectedUrl("/mypage/information")); // Expect redirect URL

		// Verify that memberService.updateMember() was called with correct arguments
		verify(memberService).updateMember(argThat(request ->
			request.name().equals(name) && request.number().equals(number)));
	}

	@Test
	void testUpdateMemberPassword() throws Exception {
		// Mock data
		String loginPassword = "!@#123Password";
		String loginPasswordConfirm = "!@#123Password";

		// Perform POST request to "/mypage/information/password"
		mockMvc.perform(post("/mypage/information/password")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("loginPassword", loginPassword)
				.param("loginPasswordConfirm", loginPasswordConfirm))
			.andExpect(status().is3xxRedirection()) // Expect HTTP redirect status
			.andExpect(redirectedUrl("/mypage/information")); // Expect redirect URL

		// Verify that memberService.updateMemberPassword() was called with correct arguments
		verify(memberService).updateMemberPassword(argThat(request ->
			request.loginPassword().equals(loginPassword) && request.loginPasswordConfirm().equals(loginPasswordConfirm)));
	}
}

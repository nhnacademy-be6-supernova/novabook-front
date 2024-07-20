package store.novabook.front.api.member.member.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.servlet.http.HttpServletResponse;
import store.novabook.front.api.member.member.dto.request.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.ForbiddenException;

class MemberControllerTest {

	@Mock
	private MemberService memberService;

	@InjectMocks
	private MemberController memberController;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
	}


	@Test
	void register_ShouldRedirectToLogin() throws Exception {
		CreateMemberRequest newMemberRequest = CreateMemberRequest.builder()
			.loginId("id")
			.loginPassword("!@#123password")
			.loginPasswordConfirm("!@#123password")
			.name("김김김")
			.number("01012345678")
			.email("aaa")
			.emailDomain("naver.com")
			.birthYear(2000)
			.birthMonth(1)
			.birthDay(1)
			.address("home")
			.build();
		when(memberService.createMember(any(CreateMemberRequest.class))).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/users/user/form")
				.flashAttr("createMemberRequest", newMemberRequest))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/login"));

		verify(memberService).createMember(any(CreateMemberRequest.class));
	}

	@Test
	void login_ShouldRedirectToHomeOnSuccess() throws Exception {
		LoginMembersRequest loginMembersRequest = new LoginMembersRequest("id", "password");
		when(memberService.login(any(LoginMembersRequest.class), any(HttpServletResponse.class)))
			.thenReturn("redirect:/");

		mockMvc.perform(MockMvcRequestBuilders.post("/users/user/form/login")
				.flashAttr("loginMembersRequest", loginMembersRequest))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/"));

		verify(memberService).login(any(LoginMembersRequest.class), any(HttpServletResponse.class));
	}

	@Test
	void login_ShouldRedirectToLoginOnFailure() throws Exception {
		LoginMembersRequest loginMembersRequest = new LoginMembersRequest("id", "password");
		doThrow(new ForbiddenException(ErrorCode.FORBIDDEN))
			.when(memberService).login(any(LoginMembersRequest.class), any(HttpServletResponse.class));

		mockMvc.perform(MockMvcRequestBuilders.post("/users/user/form/login")
				.flashAttr("loginMembersRequest", loginMembersRequest))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/login"))
			.andExpect(MockMvcResultMatchers.flash().attributeExists("error"));

		verify(memberService).login(any(LoginMembersRequest.class), any(HttpServletResponse.class));
	}

	@Test
	void paycoLinkLogin_ShouldRedirectToHome() throws Exception {
		LoginMembersRequest loginMembersRequest = new LoginMembersRequest("id", "password");
		when(memberService.login(any(LoginMembersRequest.class), any(HttpServletResponse.class))).thenReturn("redirect:/");

		mockMvc.perform(MockMvcRequestBuilders.post("/users/user/form/payco/link/login")
				.flashAttr("loginMembersRequest", loginMembersRequest))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/"));

		verify(memberService).login(any(LoginMembersRequest.class), any(HttpServletResponse.class));
	}

	@Test
	void logout_ShouldRedirectToHome() throws Exception {
		doNothing().when(memberService).logout(any(HttpServletResponse.class));

		mockMvc.perform(MockMvcRequestBuilders.get("/users/user/form/logout"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.redirectedUrl("/"));

		verify(memberService).logout(any(HttpServletResponse.class));
	}
}

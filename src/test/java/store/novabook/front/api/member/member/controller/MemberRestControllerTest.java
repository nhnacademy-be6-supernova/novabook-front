package store.novabook.front.api.member.member.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import store.novabook.front.api.member.member.dto.request.DuplicateEmailRequest;
import store.novabook.front.api.member.member.dto.request.DuplicateLoginIdRequest;
import store.novabook.front.api.member.member.dto.response.DuplicateResponse;
import store.novabook.front.api.member.member.dto.response.MemberNameResponse;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.service.MemberRestService;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;
import store.novabook.front.common.security.aop.dto.GetMembersTokenResponse;

@WebMvcTest(controllers = MemberRestController.class)
public class MemberRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MemberRestService memberRestService;

	@MockBean
	private MemberAuthClient memberAuthClient;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new MemberRestController(memberRestService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void isCreatableLoginId_ShouldReturnDuplicateResponse() throws Exception {
		// Given
		DuplicateLoginIdRequest request = new DuplicateLoginIdRequest("testLoginId");
		DuplicateResponse response = new DuplicateResponse(true);

		when(memberRestService.isDuplicateLoginId(any(DuplicateLoginIdRequest.class))).thenReturn(response);

		// When
		mockMvc.perform(post("/api/v1/front/members/login-id/is-duplicate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			// Then
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.isDuplicate").value(true));
	}

	@Test
	void isCreatableEmail_ShouldReturnDuplicateResponse() throws Exception {
		// Given
		DuplicateEmailRequest request = new DuplicateEmailRequest("test@example.com");
		DuplicateResponse response = new DuplicateResponse(false);

		when(memberRestService.isDuplicateEmail(any(DuplicateEmailRequest.class))).thenReturn(response);

		// When
		mockMvc.perform(post("/api/v1/front/members/email/is-duplicate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			// Then
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.isDuplicate").value(false));
	}

	@Test
	void getCurrentLoginId_ShouldReturnMemberId() throws Exception {
		// Given
		ApiResponse<GetMembersTokenResponse> apiResponse = new ApiResponse<>("SUCCESS", true, new GetMembersTokenResponse(1L));
		when(memberAuthClient.token()).thenReturn(apiResponse);

		// When
		mockMvc.perform(get("/api/v1/front/members/login-id/current")
				.cookie(new Cookie("Authorization", "token")))
			// Then
			.andExpect(status().isOk())
			.andExpect(content().string("1"));
	}
	@Test
	void getMemberName_ShouldReturnMemberNameResponse() throws Exception {
		// Given
		MemberNameResponse response = new MemberNameResponse("John Doe");

		when(memberRestService.getMemberName()).thenReturn(response);

		// When
		mockMvc.perform(get("/api/v1/front/members/member-name"))
			// Then
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.memberName").value("John Doe"));
	}
}

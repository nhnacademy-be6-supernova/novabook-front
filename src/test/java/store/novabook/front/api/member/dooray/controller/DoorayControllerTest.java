package store.novabook.front.api.member.dooray.controller;

import static org.mockito.ArgumentMatchers.*;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import store.novabook.front.api.member.dooray.dto.DoorayAuthCodeRequest;
import store.novabook.front.api.member.dooray.dto.DoorayAuthRequest;
import store.novabook.front.api.member.dooray.dto.SendAuthResponse;
import store.novabook.front.api.member.dooray.service.DoorayService;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.UnauthorizedException;

class DoorayControllerTest {

	private MockMvc mockMvc;

	@Mock
	private DoorayService doorayService;

	@InjectMocks
	private DoorayController doorayController;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(doorayController).build();
	}

	@Test
	void testSendAuthCode() throws Exception {
		DoorayAuthRequest request = new DoorayAuthRequest("user@example.com");
		SendAuthResponse expectedResponse = new SendAuthResponse("인증코드가 발송되었습니다.");


		mockMvc.perform(post("/dooray/send-auth-code")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").value(expectedResponse.message()));
	}

	@Test
	void testConfirm() throws Exception {
		DoorayAuthCodeRequest request = new DoorayAuthCodeRequest("user@example.com", "123456");

		doThrow(new UnauthorizedException(ErrorCode.UNAUTHORIZED_CODE)).when(doorayService).confirmAuthCode(any(DoorayAuthCodeRequest.class));

		mockMvc.perform(post("/dooray/confirm")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)); // 콘텐츠 타입 무시
	}

}

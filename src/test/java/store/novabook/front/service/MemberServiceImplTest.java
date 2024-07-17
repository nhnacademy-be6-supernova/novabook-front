
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import store.novabook.front.api.member.member.dto.request.GetMembersStatusResponse;
import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
import store.novabook.front.api.member.member.dto.response.GetMembersStatusRequest;
import store.novabook.front.api.member.member.dto.response.LoginMembersResponse;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.api.member.member.service.impl.MemberServiceImpl;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.ForbiddenException;
import store.novabook.front.common.response.ApiResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MemberServiceImplTest {

	@InjectMocks
	private MemberServiceImpl memberService;

	@Mock
	private MemberClient memberClient;

	@Mock
	private MemberAuthClient memberAuthClient;

	@Mock
	private HttpServletResponse response;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void login_ShouldRedirectToDormant_WhenMemberStatusIdIs2() {
		// Arrange
		LoginMembersRequest loginRequest = new LoginMembersRequest("username", "password");
		LoginMembersResponse loginResponse = new LoginMembersResponse("Bearer accessToken", "Bearer refreshToken");
		ApiResponse<LoginMembersResponse> apiLoginResponse = new ApiResponse<>(loginResponse, HttpStatus.OK);

		GetMembersStatusResponse statusResponse = new GetMembersStatusResponse(2, "some-uuid");
		ApiResponse<GetMembersStatusResponse> apiStatusResponse = new ApiResponse<>(statusResponse, HttpStatus.OK);

		when(memberAuthClient.login(any(LoginMembersRequest.class))).thenReturn(apiLoginResponse);
		when(memberAuthClient.status(any(GetMembersStatusRequest.class))).thenReturn(apiStatusResponse);

		// Act
		String result = memberService.login(loginRequest, response);

		// Assert
		assertEquals("redirect:/dormant", result);
		verify(response).addCookie(argThat(cookie -> "UUID".equals(cookie.getName()) && "some-uuid".equals(cookie.getValue())));
	}

	@Test
	void login_ShouldRedirectToHome_WhenMemberStatusIdIs3() {
		// Arrange
		LoginMembersRequest loginRequest = new LoginMembersRequest("username", "password");
		LoginMembersResponse loginResponse = new LoginMembersResponse("Bearer accessToken", "Bearer refreshToken");
		ApiResponse<LoginMembersResponse> apiLoginResponse = new ApiResponse<>(loginResponse, HttpStatus.OK);

		GetMembersStatusResponse statusResponse = new GetMembersStatusResponse(3, null);
		ApiResponse<GetMembersStatusResponse> apiStatusResponse = new ApiResponse<>(statusResponse, HttpStatus.OK);

		when(memberAuthClient.login(any(LoginMembersRequest.class))).thenReturn(apiLoginResponse);
		when(memberAuthClient.status(any(GetMembersStatusRequest.class))).thenReturn(apiStatusResponse);

		// Act
		String result = memberService.login(loginRequest, response);

		// Assert
		assertEquals("redirect:/", result);
	}

	@Test
	void login_ShouldSetCookies_WhenAuthorizationAndRefreshTokenAreValid() {
		// Arrange
		LoginMembersRequest loginRequest = new LoginMembersRequest("username", "password");
		LoginMembersResponse loginResponse = new LoginMembersResponse("Bearer accessToken", "Bearer refreshToken");
		ApiResponse<LoginMembersResponse> apiLoginResponse = new ApiResponse<>(loginResponse, HttpStatus.OK);

		GetMembersStatusResponse statusResponse = new GetMembersStatusResponse(1, null); // MemberStatusId is not 2 or 3
		ApiResponse<GetMembersStatusResponse> apiStatusResponse = new ApiResponse<>(statusResponse, HttpStatus.OK);

		when(memberAuthClient.login(any(LoginMembersRequest.class))).thenReturn(apiLoginResponse);
		when(memberAuthClient.status(any(GetMembersStatusRequest.class))).thenReturn(apiStatusResponse);

		// Act
		String result = memberService.login(loginRequest, response);

		// Assert
		assertEquals("redirect:/", result);
		verify(response).addCookie(argThat(cookie -> "Authorization".equals(cookie.getName()) && "accessToken".equals(cookie.getValue())));
		verify(response).addCookie(argThat(cookie -> "Refresh".equals(cookie.getName()) && "refreshToken".equals(cookie.getValue())));
	}

	@Test
	void login_ShouldThrowForbiddenException_WhenAuthorizationTokenIsEmpty() {
		// Arrange
		LoginMembersRequest loginRequest = new LoginMembersRequest("username", "password");
		LoginMembersResponse loginResponse = new LoginMembersResponse("", "Bearer refreshToken");
		ApiResponse<LoginMembersResponse> apiLoginResponse = new ApiResponse<>(loginResponse, HttpStatus.OK);

		GetMembersStatusResponse statusResponse = new GetMembersStatusResponse(1, null);
		ApiResponse<GetMembersStatusResponse> apiStatusResponse = new ApiResponse<>(statusResponse, HttpStatus.OK);

		when(memberAuthClient.login(any(LoginMembersRequest.class))).thenReturn(apiLoginResponse);
		when(memberAuthClient.status(any(GetMembersStatusRequest.class))).thenReturn(apiStatusResponse);

		// Act & Assert
		ForbiddenException thrown = assertThrows(ForbiddenException.class, () -> {
			memberService.login(loginRequest, response);
		});
		assertEquals(ErrorCode.FORBIDDEN, thrown.getErrorCode());
	}
}

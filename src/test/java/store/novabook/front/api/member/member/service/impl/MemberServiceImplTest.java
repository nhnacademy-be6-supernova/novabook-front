package store.novabook.front.api.member.member.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletResponse;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.dto.request.GetMembersStatusResponse;
import store.novabook.front.api.member.member.dto.request.IsExpireAccessTokenRequest;
import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
import store.novabook.front.api.member.member.dto.response.GetMembersStatusRequest;
import store.novabook.front.api.member.member.dto.response.IsExpireAccessTokenResponse;
import store.novabook.front.api.member.member.dto.response.LoginMembersResponse;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.ForbiddenException;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.util.CookieUtil;

//////////////////////////////////////////////////////
//////////////////////////////////////////////////////
//////////////////////////////////////////////////////
//////////////////////////////////////////////////////

//이거 순서 정의 되어 있습니다
//로그아웃 테스트는 맨 마지막에
//@Order로 지정하세요

//////////////////////////////////////////////////////
//////////////////////////////////////////////////////
//////////////////////////////////////////////////////
//////////////////////////////////////////////////////


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberServiceImplTest {

	@InjectMocks
	private MemberServiceImpl memberService;

	@Mock
	private MemberClient memberClient;

	@Mock
	private MemberAuthClient memberAuthClient;

	@Mock
	private HttpServletResponse response;

	@Mock
	private CookieUtil cookieUtil;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@Order(1)
	void login_ShouldRedirectToDormant_WhenMemberStatusIdIs2() {
		// Arrange
		LoginMembersRequest loginRequest = new LoginMembersRequest("username", "password");
		LoginMembersResponse loginResponse = new LoginMembersResponse("Bearer accessToken", "Bearer refreshToken");
		ApiResponse<LoginMembersResponse> apiLoginResponse = new ApiResponse<>("SUCCESS", true, loginResponse);

		GetMembersStatusResponse statusResponse = new GetMembersStatusResponse(2L, "some-uuid");
		ApiResponse<GetMembersStatusResponse> apiStatusResponse = new ApiResponse<>("SUCCESS", true, statusResponse);

		when(memberAuthClient.login(any(LoginMembersRequest.class))).thenReturn(apiLoginResponse);
		when(memberAuthClient.status(any(GetMembersStatusRequest.class))).thenReturn(apiStatusResponse);

		// Act
		String result = memberService.login(loginRequest, response);

		// Assert
		assertEquals("redirect:/dormant", result);
		verify(response).addCookie(
			argThat(cookie -> "UUID".equals(cookie.getName()) && "some-uuid".equals(cookie.getValue())));
	}

	@Test
	@Order(2)
	void login_ShouldRedirectToHome_WhenMemberStatusIdIs3() {
		// Arrange
		LoginMembersRequest loginRequest = new LoginMembersRequest("username", "password");
		LoginMembersResponse loginResponse = new LoginMembersResponse("Bearer accessToken", "Bearer refreshToken");
		ApiResponse<LoginMembersResponse> apiLoginResponse = new ApiResponse<>("SUCCESS", true, loginResponse);

		GetMembersStatusResponse statusResponse = new GetMembersStatusResponse(3L, null);
		ApiResponse<GetMembersStatusResponse> apiStatusResponse = new ApiResponse<>("SUCCESS", true, statusResponse);

		when(memberAuthClient.login(any(LoginMembersRequest.class))).thenReturn(apiLoginResponse);
		when(memberAuthClient.status(any(GetMembersStatusRequest.class))).thenReturn(apiStatusResponse);

		// Act
		String result = memberService.login(loginRequest, response);

		// Assert
		assertEquals("redirect:/", result);
	}

	@Test
	@Order(3)
	void login_ShouldSetCookies_WhenAuthorizationAndRefreshTokenAreValid() {
		// Arrange
		LoginMembersRequest loginRequest = new LoginMembersRequest("username", "password");
		LoginMembersResponse loginResponse = new LoginMembersResponse("Bearer accessToken", "Bearer refreshToken");
		ApiResponse<LoginMembersResponse> apiLoginResponse = new ApiResponse<>("SUCCESS", true, loginResponse);

		GetMembersStatusResponse statusResponse = new GetMembersStatusResponse(1L,
			null); // MemberStatusId is not 2 or 3
		ApiResponse<GetMembersStatusResponse> apiStatusResponse = new ApiResponse<>("SUCCESS", true, statusResponse);

		when(memberAuthClient.login(any(LoginMembersRequest.class))).thenReturn(apiLoginResponse);
		when(memberAuthClient.status(any(GetMembersStatusRequest.class))).thenReturn(apiStatusResponse);

		// Act
		String result = memberService.login(loginRequest, response);

		// Assert
		assertEquals("redirect:/", result);
		verify(response).addCookie(
			argThat(cookie -> "Authorization".equals(cookie.getName()) && "accessToken".equals(cookie.getValue())));
		verify(response).addCookie(
			argThat(cookie -> "Refresh".equals(cookie.getName()) && "refreshToken".equals(cookie.getValue())));
	}

	@Test
	@Order(4)
	void login_ShouldThrowForbiddenException_WhenAuthorizationTokenIsEmpty() {
		// Arrange
		LoginMembersRequest loginRequest = new LoginMembersRequest("username", "password");
		LoginMembersResponse loginResponse = new LoginMembersResponse("", "Bearer refreshToken");
		ApiResponse<LoginMembersResponse> apiLoginResponse = new ApiResponse<>("SUCCESS", true, loginResponse);

		GetMembersStatusResponse statusResponse = new GetMembersStatusResponse(1L, null);
		ApiResponse<GetMembersStatusResponse> apiStatusResponse = new ApiResponse<>("SUCCESS", true, statusResponse);

		when(memberAuthClient.login(any(LoginMembersRequest.class))).thenReturn(apiLoginResponse);
		when(memberAuthClient.status(any(GetMembersStatusRequest.class))).thenReturn(apiStatusResponse);

		// Act & Assert
		ForbiddenException thrown = assertThrows(ForbiddenException.class, () -> {
			memberService.login(loginRequest, response);
		});
		assertEquals(ErrorCode.FORBIDDEN, thrown.getErrorCode());
	}



	@Test
	@Order(6)
	void testIsExpireAccessToken() {
		// Given
		IsExpireAccessTokenRequest request = new IsExpireAccessTokenRequest("someToken");
		IsExpireAccessTokenResponse expectedResponse = new IsExpireAccessTokenResponse(true);

		when(memberAuthClient.expire(request)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		IsExpireAccessTokenResponse actualResponse = memberService.isExpireAccessToken(request);

		// Then
		assertNotNull(actualResponse);
		assertEquals(expectedResponse.isExpire(), actualResponse.isExpire());
		verify(memberAuthClient).expire(request);
	}

	@Test
	@Order(7)
	void testNewToken() {
		// Given
		GetNewTokenRequest request = new GetNewTokenRequest("someRefreshToken");
		GetNewTokenResponse expectedResponse = new GetNewTokenResponse("newAccessToken");

		// Mock the behavior of memberAuthClient.newToken
		when(memberAuthClient.newToken(request))
			.thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		GetNewTokenResponse actualResponse = memberService.newToken(request);

		// Then
		assertNotNull(actualResponse);
		assertEquals(expectedResponse.accessToken(), actualResponse.accessToken());
		verify(memberAuthClient).newToken(request);
	}

	@Test
	@Order(8)
	void logout_ShouldCallLogoutAndDeleteAuthorizationCookie() {
		// Act
		memberService.logout(response);

		// Assert
		verify(memberAuthClient).logout();
		CookieUtil.deleteAuthorizationCookie(response);  // Verify the method call
	}
}

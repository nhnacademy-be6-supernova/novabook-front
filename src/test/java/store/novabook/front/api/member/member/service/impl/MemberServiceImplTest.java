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
import store.novabook.front.api.member.member.dto.request.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.request.DeleteMemberRequest;
import store.novabook.front.api.member.member.dto.request.GetMembersStatusResponse;
import store.novabook.front.api.member.member.dto.request.IsExpireAccessTokenRequest;
import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberPasswordRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberRequest;
import store.novabook.front.api.member.member.dto.response.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.response.GetMemberResponse;
import store.novabook.front.api.member.member.dto.response.GetMembersStatusRequest;
import store.novabook.front.api.member.member.dto.response.IsExpireAccessTokenResponse;
import store.novabook.front.api.member.member.dto.response.LoginMembersResponse;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.ForbiddenException;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.util.CookieUtil;

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

	@Test
	void createMember_ShouldReturnCreateMemberResponse() {
		// Arrange
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

		CreateMemberResponse expectedResponse = new CreateMemberResponse(1L);
		ApiResponse<CreateMemberResponse> apiResponse = new ApiResponse<>("SUCCESS", true, expectedResponse);

		when(memberClient.createMember(any(CreateMemberRequest.class))).thenReturn(apiResponse);

		// Act
		CreateMemberResponse actualResponse = memberService.createMember(newMemberRequest);

		// Assert
		assertNotNull(actualResponse);
		assertEquals(expectedResponse, actualResponse);
		verify(memberClient, times(1)).createMember(any(CreateMemberRequest.class));
	}

	@Test
	void getMemberById_ShouldReturnGetMemberResponse() {
		// Arrange
		GetMemberResponse expectedResponse = new GetMemberResponse(1L, "id", 2000, 1, 1, "01012345678", "김",
			"aa@naver.com");
		ApiResponse<GetMemberResponse> apiResponse = new ApiResponse<>("SUCCESS", true, expectedResponse);

		when(memberClient.getMember()).thenReturn(apiResponse);

		// Act
		GetMemberResponse actualResponse = memberService.getMemberById();

		// Assert
		assertNotNull(actualResponse);
		assertEquals(expectedResponse, actualResponse);
		verify(memberClient, times(1)).getMember();
	}

	@Test
	void updateMember_ShouldCallUpdateMember() {
		// Arrange
		UpdateMemberRequest updateMemberRequest = new UpdateMemberRequest("aa", "123");

		// Act
		memberService.updateMember(updateMemberRequest);

		// Assert
		verify(memberClient, times(1)).updateMember(any(UpdateMemberRequest.class));
	}

	@Test
	void updateMemberPassword_ShouldCallUpdateMemberPassword() {
		// Arrange
		UpdateMemberPasswordRequest updateMemberPasswordRequest = new UpdateMemberPasswordRequest("!@#123password", "!@#123password");

		// Act
		memberService.updateMemberPassword(updateMemberPasswordRequest);

		// Assert
		verify(memberClient, times(1)).updateMemberPassword(any(UpdateMemberPasswordRequest.class));
	}

	@Test
	void deleteMember_ShouldCallUpdateMemberStatusToWithdraw() {
		// Arrange
		DeleteMemberRequest deleteMemberRequest = new DeleteMemberRequest("!@#123password");

		// Act
		memberService.deleteMember(deleteMemberRequest);

		// Assert
		verify(memberClient, times(1)).updateMemberStatusToWithdraw(any(DeleteMemberRequest.class));
	}
}

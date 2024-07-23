package store.novabook.front.api.member.member.service.impl;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.ForbiddenException;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.util.CookieUtil;
import store.novabook.front.common.util.LoginCookieUtil;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberClient memberClient;
	private final MemberAuthClient memberAuthClient;

	@Override
	public CreateMemberResponse createMember(CreateMemberRequest createMemberRequest) {

		CreateMemberRequest newMemberRequest = CreateMemberRequest.builder()
			.loginId(createMemberRequest.loginId())
			.loginPassword(createMemberRequest.loginPassword())
			.loginPasswordConfirm(createMemberRequest.loginPasswordConfirm())
			.name(createMemberRequest.name())
			.number(createMemberRequest.number())
			.email(createMemberRequest.email())
			.emailDomain(createMemberRequest.emailDomain())
			.birthYear(createMemberRequest.birthYear())
			.birthMonth(createMemberRequest.birthMonth())
			.birthDay(createMemberRequest.birthDay())
			.address(createMemberRequest.address())
			.build();
		ApiResponse<CreateMemberResponse> createMemberResponse = memberClient.createMember(newMemberRequest);
		return createMemberResponse.getBody();
	}

	@Override
	public String login(@Valid LoginMembersRequest loginMembersRequest, HttpServletResponse response) {
		ApiResponse<LoginMembersResponse> loginMembersResponse = memberAuthClient.login(loginMembersRequest);

		GetMembersStatusRequest getMembersStatusRequest = new GetMembersStatusRequest(
			loginMembersResponse.getBody().accessToken());
		ApiResponse<GetMembersStatusResponse> status = memberAuthClient.status(getMembersStatusRequest);

		if (status.getBody().memberStatusId() == 2) {
			Cookie uuidCookie = new Cookie("UUID", status.getBody().uuid());
			uuidCookie.setMaxAge(60 * 60 * 3);
			uuidCookie.setSecure(false);
			uuidCookie.setHttpOnly(false);
			uuidCookie.setPath("/");
			response.addCookie(uuidCookie);
			return "redirect:/dormant";
		} else if (status.getBody().memberStatusId() == 3) {
			return "redirect:/";
		}

		String authorization = loginMembersResponse.getBody().accessToken();
		String refresh = loginMembersResponse.getBody().refreshToken();

		if (authorization != null && !authorization.isEmpty()) {
			String accessToken = authorization.replace("Bearer ", "");
			String refreshToken = refresh.replace("Bearer ", "");

			LoginCookieUtil.createAccessTokenCookie(response, accessToken);
			LoginCookieUtil.createRefreshTokenCookie(response, refreshToken);

		} else {
			throw new ForbiddenException(ErrorCode.FORBIDDEN);
		}

		return "redirect:/";
	}

	@Override
	public void logout(HttpServletResponse response) {
		memberAuthClient.logout();
		LoginCookieUtil.deleteAuthorizationCookie(response);
	}

	@Override
	public IsExpireAccessTokenResponse isExpireAccessToken(IsExpireAccessTokenRequest isExpireAccessTokenRequest) {
		ApiResponse<IsExpireAccessTokenResponse> isExpireAccessTokenResponse = memberAuthClient.expire(
			isExpireAccessTokenRequest);
		return isExpireAccessTokenResponse.getBody();
	}

	@Override
	public GetMemberResponse getMemberById() {
		return memberClient.getMember().getBody();
	}

	@Override
	public void updateMember(UpdateMemberRequest updateMemberRequest) {
		memberClient.updateMember(updateMemberRequest);
	}

	@Override
	public void updateMemberPassword(UpdateMemberPasswordRequest updateMemberPasswordRequest) {
		memberClient.updateMemberPassword(updateMemberPasswordRequest);
	}

	@Override
	public void deleteMember(DeleteMemberRequest deleteMemberRequest) {
		memberClient.updateMemberStatusToWithdraw(deleteMemberRequest);
	}

	@Override
	public GetNewTokenResponse newToken(GetNewTokenRequest getNewTokenRequest) {
		ApiResponse<GetNewTokenResponse> getNewTokenResponseResponseEntity = memberAuthClient.newToken(
			getNewTokenRequest);
		return getNewTokenResponseResponseEntity.getBody();
	}

}

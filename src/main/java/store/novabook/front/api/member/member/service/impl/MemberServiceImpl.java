package store.novabook.front.api.member.member.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.dto.request.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.request.DeleteMemberRequest;
import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberPasswordRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberRequest;
import store.novabook.front.api.member.member.dto.response.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.response.GetMemberResponse;
import store.novabook.front.api.member.member.dto.response.LoginMembersResponse;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.response.ApiResponse;

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
	public void login(@Valid LoginMembersRequest loginMembersRequest, HttpServletResponse response) {
		ResponseEntity<LoginMembersResponse> loginMembersResponse = memberAuthClient.login(loginMembersRequest);
		if (!loginMembersResponse.getStatusCode().is2xxSuccessful()) {
			return;
		}

		String authorization = loginMembersResponse.getBody().accessToken();
		String refresh = loginMembersResponse.getBody().refreshToken();

		if (authorization != null && !authorization.isEmpty()) {
			String accessToken = authorization.replace("Bearer ", "");
			String refreshToken = refresh.replace("Bearer ", "");

			Cookie accessCookie = new Cookie("Authorization", accessToken);
			accessCookie.setMaxAge(60 * 60 * 24 * 7);
			accessCookie.setPath("/");
			response.addCookie(accessCookie);

			Cookie refreshCookie = new Cookie("Refresh", refreshToken);
			refreshCookie.setMaxAge(60 * 60 * 24 * 7);
			refreshCookie.setPath("/");
			response.addCookie(refreshCookie);

		} else {
			throw new RuntimeException("로그인 실패");
		}
	}

	@Override
	public void logout() {
		memberAuthClient.logout();
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
		ResponseEntity<GetNewTokenResponse> getNewTokenResponseResponseEntity = memberAuthClient.newToken(
			getNewTokenRequest);
		return getNewTokenResponseResponseEntity.getBody();

	}

}

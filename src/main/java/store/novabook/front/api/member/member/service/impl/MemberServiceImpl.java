package store.novabook.front.api.member.member.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.dto.request.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.request.DeleteMemberRequest;
import store.novabook.front.api.member.member.dto.request.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberNameRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberNumberRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberPasswordRequest;
import store.novabook.front.api.member.member.dto.response.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.response.GetMemberResponse;
import store.novabook.front.api.member.member.dto.response.LoginMemberResponse;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.response.ApiResponse;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberClient memberClient;
	private final MemberAuthClient memberAuthClient;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public CreateMemberResponse createMember(CreateMemberRequest createMemberRequest) {

		CreateMemberRequest newMemberRequest = CreateMemberRequest.builder()
			.loginId(createMemberRequest.loginId())
			.loginPassword(passwordEncoder.encode(createMemberRequest.loginPassword()))
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
	public LoginMemberResponse getMember(@Valid LoginMemberRequest loginMemberRequest, HttpServletResponse response) {
		ResponseEntity<LoginMemberResponse> tokenDtoApiResponse = memberAuthClient.login(loginMemberRequest);

		// 헤더 설정
		response.setHeader("Authorization", tokenDtoApiResponse.getHeaders().getFirst("Authorization"));
		response.setHeader("Cookie", tokenDtoApiResponse.getHeaders().getFirst("Cookie"));

		return tokenDtoApiResponse.getBody();
	}


	@Override
	public GetMemberResponse getMemberById(Long memberId) {
		return memberClient.getMember(memberId).getBody();
	}

	@Override
	public void updateMemberName(Long memberId, UpdateMemberNameRequest updateMemberNameRequest) {
		memberClient.updateMemberName(memberId, updateMemberNameRequest);
	}

	@Override
	public void updateMemberNumber(Long memberId, UpdateMemberNumberRequest updateMemberNumberRequest) {
		memberClient.updateMemberNumber(memberId, updateMemberNumberRequest);
	}

	@Override
	public void updateMemberPassword(Long memberId, UpdateMemberPasswordRequest updateMemberPasswordRequest) {
		memberClient.updateMemberPassword(memberId, updateMemberPasswordRequest);
	}

	@Override
	public void deleteMember(Long memberId, DeleteMemberRequest deleteMemberRequest) {
		memberClient.updateMemberStatusToWithdraw(memberId, deleteMemberRequest);
	}

	@Override
	public GetNewTokenResponse newToken(GetNewTokenRequest getNewTokenRequest) {
		return memberAuthClient.newToken(getNewTokenRequest).getBody();

	}

	// @Override
	// public LoginMemberResponse getMember(LoginMemberRequest loginMemberRequest) {
	// 	ResponseEntity<LoginMemberResponse> tokenDtoApiResponse = memberClient.login(loginMemberRequest);
	// 	return tokenDtoApiResponse.getBody();
	//
	// }
}

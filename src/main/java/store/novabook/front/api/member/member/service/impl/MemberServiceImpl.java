package store.novabook.front.api.member.member.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.ApiResponse;
import store.novabook.front.api.member.member.MemberClient;
import store.novabook.front.api.member.member.dto.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.LoginMemberResponse;
import store.novabook.front.api.member.member.service.MemberService;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberClient memberClient;

	@Override
	public CreateMemberResponse createMember(CreateMemberRequest createMemberRequest) {
		String fullEmail = createMemberRequest.getEmailFull();

		CreateMemberRequest newMemberRequest = CreateMemberRequest.builder()
			.loginId(createMemberRequest.loginId())
			.loginPassword(createMemberRequest.loginPassword())
			.name(createMemberRequest.name())
			.number(createMemberRequest.number())
			.email(fullEmail)
			.emailDomain(null)
			.birthYear(createMemberRequest.birthYear())
			.birthMonth(createMemberRequest.birthMonth())
			.birthDay(createMemberRequest.birthDay())
			.address(createMemberRequest.address())
			.build();
		ApiResponse<CreateMemberResponse> createMemberResponse = memberClient.createMember(newMemberRequest);
		return createMemberResponse.getBody();
	}

	// @Override
	// public LoginMemberResponse login(LoginMemberRequest loginMemberRequest) {
	//
	// 	ApiResponse<LoginMemberResponse> loginMemberResponseApiResponse = memberClient.login(loginMemberRequest);
	// 	return loginMemberResponseApiResponse.getBody();
	//
	// }

	@Override
	public LoginMemberResponse getMember(LoginMemberRequest loginMemberRequest) {
		ResponseEntity<LoginMemberResponse> tokenDtoApiResponse = memberClient.login(loginMemberRequest);
		return tokenDtoApiResponse.getBody();

	}
}

package store.novabook.front.api.member.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.ApiResponse;
import store.novabook.front.api.member.MemberClient;
import store.novabook.front.api.member.dto.CreateMemberRequest;
import store.novabook.front.api.member.dto.CreateMemberResponse;
import store.novabook.front.api.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.dto.LoginMemberResponse;
import store.novabook.front.api.member.service.MemberService;

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

		// LoginMemberRequest testLoginMemberRequest = LoginMemberRequest.builder()
		// 	.username("1")
		// 	.password("12")
		// 	.build();
		// ApiResponse<LoginMemberResponse> loginMemberResponseApiResponse = memberClient.login(testLoginMemberRequest);
		ResponseEntity<LoginMemberResponse> tokenDtoApiResponse = memberClient.login(loginMemberRequest);
		// LoginMemberResponse loginMemberResponse = LoginMemberResponse.builder()
		// 	.success(true)
		// 	.memberId(1L)
		// 	.name("test")
		// 	.build();
		return tokenDtoApiResponse.getBody();

	}
}

package store.novabook.front.api.member.member.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.MemberClient;
import store.novabook.front.api.member.member.dto.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.LoginMemberResponse;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.response.ApiResponse;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberClient memberClient;

	@Override
	public CreateMemberResponse createMember(CreateMemberRequest createMemberRequest) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
	public LoginMemberResponse getMember(LoginMemberRequest loginMemberRequest, HttpServletResponse response) {
		ResponseEntity<LoginMemberResponse> tokenDtoApiResponse = memberClient.login(loginMemberRequest);
		response.setHeader("Authorization", tokenDtoApiResponse.getHeaders().getFirst("Authorization"));
		response.setHeader("Cookie", tokenDtoApiResponse.getHeaders().getFirst("Cookie"));
		return tokenDtoApiResponse.getBody();
	}

	// @Override
	// public LoginMemberResponse getMember(LoginMemberRequest loginMemberRequest) {
	// 	ResponseEntity<LoginMemberResponse> tokenDtoApiResponse = memberClient.login(loginMemberRequest);
	// 	return tokenDtoApiResponse.getBody();
	//
	// }
}

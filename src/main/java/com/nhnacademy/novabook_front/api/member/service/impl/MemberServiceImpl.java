package com.nhnacademy.novabook_front.api.member.service.impl;

import org.springframework.stereotype.Service;

import com.nhnacademy.novabook_front.api.ApiResponse;
import com.nhnacademy.novabook_front.api.member.MemberClient;
import com.nhnacademy.novabook_front.api.member.dto.CreateMemberRequest;

import com.nhnacademy.novabook_front.api.member.dto.CreateMemberResponse;
import com.nhnacademy.novabook_front.api.member.dto.LoginMemberRequest;
import com.nhnacademy.novabook_front.api.member.dto.LoginMemberResponse;
import com.nhnacademy.novabook_front.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;

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

	@Override
	public LoginMemberResponse login(LoginMemberRequest loginMemberRequest) {

		ApiResponse<LoginMemberResponse> loginMemberResponseApiResponse = memberClient.login(loginMemberRequest);
		return loginMemberResponseApiResponse.getBody();

	}
}

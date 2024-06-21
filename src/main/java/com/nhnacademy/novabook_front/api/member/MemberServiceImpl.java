package com.nhnacademy.novabook_front.api.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberClient memberClient;

	@Override
	public ResponseEntity<CreateMemberResponse> createMember(CreateMemberRequest createMemberRequest) {
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
		ResponseEntity<CreateMemberResponse> createMemberResponse = memberClient.createMember(newMemberRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(createMemberResponse.getBody());
	}
}

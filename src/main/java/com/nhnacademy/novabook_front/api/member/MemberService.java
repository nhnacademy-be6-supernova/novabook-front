package com.nhnacademy.novabook_front.api.member;

import org.springframework.http.ResponseEntity;

public interface MemberService {
	ResponseEntity<CreateMemberResponse> createMember(CreateMemberRequest createMemberRequest);
}

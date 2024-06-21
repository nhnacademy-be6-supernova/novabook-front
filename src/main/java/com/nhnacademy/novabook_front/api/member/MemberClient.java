package com.nhnacademy.novabook_front.api.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "memberClient", url = "http://localhost:8090/members")
public interface MemberClient {

	@PostMapping
	ResponseEntity<CreateMemberResponse> createMember(@RequestBody CreateMemberRequest createMemberRequest);
}

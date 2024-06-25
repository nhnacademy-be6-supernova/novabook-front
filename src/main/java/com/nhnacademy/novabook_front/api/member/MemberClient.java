package com.nhnacademy.novabook_front.api.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.novabook_front.api.ApiResponse;
import com.nhnacademy.novabook_front.api.member.dto.CreateMemberRequest;
import com.nhnacademy.novabook_front.api.member.dto.CreateMemberResponse;
import com.nhnacademy.novabook_front.api.member.dto.LoginMemberRequest;
import com.nhnacademy.novabook_front.api.member.dto.LoginMemberResponse;

@FeignClient(name = "memberClient", url = "http://localhost:8090/api/v1/store/members")
public interface MemberClient {

	@PostMapping
	ApiResponse<CreateMemberResponse> createMember(@RequestBody CreateMemberRequest createMemberRequest);

	@PostMapping("/login")
	ApiResponse<LoginMemberResponse> login(@RequestBody LoginMemberRequest loginMemberRequest);


}

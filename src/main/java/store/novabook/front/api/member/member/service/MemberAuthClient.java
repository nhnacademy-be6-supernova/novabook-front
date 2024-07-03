package store.novabook.front.api.member.member.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.dto.request.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.response.LoginMemberResponse;
import store.novabook.front.common.aop.GetMembersUUIDRequest;
import store.novabook.front.common.aop.GetMembersUUIDResponse;

@FeignClient(name = "memberAuthClient")
public interface MemberAuthClient {
	@PostMapping("/login")
	ResponseEntity<LoginMemberResponse> login(@RequestBody LoginMemberRequest loginMemberRequest);


	@PostMapping("/refresh")
	ResponseEntity<GetNewTokenResponse> newToken(@RequestBody GetNewTokenRequest getNewTokenRequest);


	@PostMapping("/members/uuid")
	ResponseEntity<GetMembersUUIDResponse> uuid(@RequestBody GetMembersUUIDRequest getMembersUuidRequest);
}
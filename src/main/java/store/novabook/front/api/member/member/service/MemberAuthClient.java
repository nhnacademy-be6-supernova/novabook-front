package store.novabook.front.api.member.member.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.dto.request.GetMembersStatusResponse;
import store.novabook.front.api.member.member.dto.request.GetPaycoMembersRequest;
import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
import store.novabook.front.api.member.member.dto.response.GetPaycoMembersResponse;
import store.novabook.front.api.member.member.dto.response.LoginMembersResponse;
import store.novabook.front.common.security.aop.GetMembersTokenResponse;

@FeignClient(name = "memberAuthClient")
public interface MemberAuthClient {
	@PostMapping("/login")
	ResponseEntity<LoginMembersResponse> login(@RequestBody LoginMembersRequest loginMembersRequest);

	@PostMapping("/refresh")
	ResponseEntity<GetNewTokenResponse> newToken(@RequestBody GetNewTokenRequest getNewTokenRequest);

	@PostMapping("/members/token")
	ResponseEntity<GetMembersTokenResponse> token();

	@PostMapping("/logout")
	ResponseEntity<Void> logout();

	@PostMapping("/payco")
	ResponseEntity<GetPaycoMembersResponse> paycoLogin(@RequestBody GetPaycoMembersRequest getPaycoMembersRequest);

	@PostMapping("/members/status")
	ResponseEntity<GetMembersStatusResponse> status();

}
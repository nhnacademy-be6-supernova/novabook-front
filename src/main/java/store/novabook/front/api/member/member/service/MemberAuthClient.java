package store.novabook.front.api.member.member.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.dto.request.GetMembersStatusResponse;
import store.novabook.front.api.member.member.dto.request.GetPaycoMembersRequest;
import store.novabook.front.api.member.member.dto.request.LinkPaycoMembersUUIDRequest;
import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
import store.novabook.front.api.member.member.dto.response.GetMembersStatusRequest;
import store.novabook.front.api.member.member.dto.response.GetPaycoMembersResponse;
import store.novabook.front.api.member.member.dto.response.LoginMembersResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.security.aop.dto.GetMembersTokenResponse;

@FeignClient(name = "gateway-service", path = "/auth", contextId = "memberAuthClient")
public interface MemberAuthClient {
	@PostMapping("/login")
	ApiResponse<LoginMembersResponse> login(@RequestBody LoginMembersRequest loginMembersRequest);

	@PostMapping("/refresh")
	ApiResponse<GetNewTokenResponse> newToken(@RequestBody GetNewTokenRequest getNewTokenRequest);

	@PostMapping("/members/token")
	ApiResponse<GetMembersTokenResponse> token();

	@PostMapping("/logout")
	ApiResponse<Void> logout();

	@PostMapping("/payco")
	ApiResponse<GetPaycoMembersResponse> paycoLogin(@RequestBody GetPaycoMembersRequest getPaycoMembersRequest);

	@PostMapping("/payco/link")
	ApiResponse<Void> paycoLink(@Valid @RequestBody LinkPaycoMembersUUIDRequest linkPaycoMembersUUIDRequest);

	@PostMapping("/members/status")
	ApiResponse<GetMembersStatusResponse> status(@Valid @RequestBody GetMembersStatusRequest getMembersStatusRequest);

}
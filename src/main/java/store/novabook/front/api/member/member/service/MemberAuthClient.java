package store.novabook.front.api.member.member.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.dto.request.GetMembersRoleResponse;
import store.novabook.front.api.member.member.dto.request.GetMembersStatusResponse;
import store.novabook.front.api.member.member.dto.request.GetPaycoMembersRequest;
import store.novabook.front.api.member.member.dto.request.IsExpireAccessTokenRequest;
import store.novabook.front.api.member.member.dto.request.LinkPaycoMembersUUIDRequest;
import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
import store.novabook.front.api.member.member.dto.response.GetMembersRoleRequest;
import store.novabook.front.api.member.member.dto.response.GetMembersStatusRequest;
import store.novabook.front.api.member.member.dto.response.GetPaycoMembersResponse;
import store.novabook.front.api.member.member.dto.response.IsExpireAccessTokenResponse;
import store.novabook.front.api.member.member.dto.response.LoginMembersResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.security.aop.dto.GetMembersTokenResponse;

@FeignClient(name = "gateway-service", path = "/auth", contextId = "memberAuthClient")
public interface MemberAuthClient {
	@PostMapping("/login")
	ApiResponse<LoginMembersResponse> login(@RequestBody LoginMembersRequest loginMembersRequest);

	@PostMapping("/refresh")
	ApiResponse<GetNewTokenResponse> newToken(@RequestBody GetNewTokenRequest getNewTokenRequest);

	@PostMapping("/expire")
	ApiResponse<IsExpireAccessTokenResponse> expire(
		@RequestBody IsExpireAccessTokenRequest isExpireAccessTokenRequest);

	@PostMapping("/members/token")
	ApiResponse<GetMembersTokenResponse> token();

	@PostMapping("/logout")
	ApiResponse<Void> logout();

	@PostMapping("/payco")
	ApiResponse<GetPaycoMembersResponse> paycoLogin(@RequestBody GetPaycoMembersRequest getPaycoMembersRequest);

	@PostMapping("/payco/link")
	ApiResponse<Void> paycoLink(@RequestBody LinkPaycoMembersUUIDRequest linkPaycoMembersUUIDRequest);

	@PostMapping("/members/status")
	ApiResponse<GetMembersStatusResponse> status(@RequestBody GetMembersStatusRequest getMembersStatusRequest);

	@PostMapping("/role")
	ApiResponse<GetMembersRoleResponse> getRole(@RequestBody GetMembersRoleRequest getMembersRoleRequest);

}
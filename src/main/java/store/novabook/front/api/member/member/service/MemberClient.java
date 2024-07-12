package store.novabook.front.api.member.member.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.member.member.dto.request.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.request.DeleteMemberRequest;
import store.novabook.front.api.member.member.dto.request.DuplicateEmailRequest;
import store.novabook.front.api.member.member.dto.request.DuplicateLoginIdRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberPasswordRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberRequest;
import store.novabook.front.api.member.member.dto.response.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.response.DuplicateResponse;
import store.novabook.front.api.member.member.dto.response.GetMemberResponse;
import store.novabook.front.api.member.member.dto.response.MemberNameResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/members", contextId = "memberClient")
public interface MemberClient {

	@PostMapping
	ApiResponse<CreateMemberResponse> createMember(@RequestBody CreateMemberRequest createMemberRequest);

	@GetMapping("/member")
	ApiResponse<GetMemberResponse> getMember();

	@PutMapping("/member/update")
	ApiResponse<Void> updateMember(@RequestBody UpdateMemberRequest updateMemberRequest);

	@PutMapping("/member/password")
	ApiResponse<Void> updateMemberPassword(@RequestBody UpdateMemberPasswordRequest updateMemberPasswordRequest);

	@PutMapping("/member/withdraw")
	ApiResponse<Void> updateMemberStatusToWithdraw(@RequestBody DeleteMemberRequest deleteMemberRequest);

	@PostMapping("/login-id/is-duplicate")
	ApiResponse<DuplicateResponse> isDuplicateLoginId(@RequestBody DuplicateLoginIdRequest request);

	@PostMapping("/email/is-duplicate")
	ApiResponse<DuplicateResponse> isDuplicateEmail(@RequestBody DuplicateEmailRequest request);

	@GetMapping("/member/name")
	ApiResponse<MemberNameResponse> getMemberName();
}

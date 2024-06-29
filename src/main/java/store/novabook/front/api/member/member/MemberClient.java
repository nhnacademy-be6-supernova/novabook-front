package store.novabook.front.api.member.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import store.novabook.front.api.member.member.dto.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.DeleteMemberRequest;
import store.novabook.front.api.member.member.dto.GetMemberResponse;
import store.novabook.front.api.member.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.LoginMemberResponse;
import store.novabook.front.api.member.member.dto.UpdateMemberNameRequest;
import store.novabook.front.api.member.member.dto.UpdateMemberNumberRequest;
import store.novabook.front.api.member.member.dto.UpdateMemberPasswordRequest;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "memberClient", url = "http://localhost:9777/api/v1/store/members")
public interface MemberClient {

	@PostMapping
	ApiResponse<CreateMemberResponse> createMember(@RequestBody CreateMemberRequest createMemberRequest);

	@PostMapping("/login")
	ResponseEntity<LoginMemberResponse> login(@RequestBody LoginMemberRequest loginMemberRequest);

	@GetMapping("/member")
	ApiResponse<GetMemberResponse> getMember(@RequestHeader(required = false) Long memberId);

	@PutMapping("/member/name")
	ApiResponse<Void> updateMemberName(@RequestHeader(required = false) Long memberId,
		@RequestBody UpdateMemberNameRequest updateMemberNameRequest);

	@PutMapping("/member/number")
	ApiResponse<Void> updateMemberNumber(@RequestHeader(required = false) Long memberId,
		@RequestBody UpdateMemberNumberRequest updateMemberNumberRequest);

	@PutMapping("/member/password")
	ApiResponse<Void> updateMemberPassword(@RequestHeader(required = false) Long memberId,
		@RequestBody UpdateMemberPasswordRequest updateMemberPasswordRequest);

	@PutMapping("/member/withdraw")
	ApiResponse<Void> updateMemberStatusToWithdraw(@RequestHeader(required = false) Long memberId, @RequestBody DeleteMemberRequest deleteMemberRequest);

}

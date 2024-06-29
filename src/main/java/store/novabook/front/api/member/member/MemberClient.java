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
import store.novabook.front.api.member.member.dto.GetMemberResponse;
import store.novabook.front.api.member.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.LoginMemberResponse;
import store.novabook.front.api.member.member.dto.UpdateMemberRequest;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "memberClient", url = "http://localhost:9777/api/v1/store/members")
public interface MemberClient {

	@PostMapping
	ApiResponse<CreateMemberResponse> createMember(@RequestBody CreateMemberRequest createMemberRequest);

	@GetMapping("/member")
	ApiResponse<GetMemberResponse> getMember(@RequestHeader Long memberId);

	@PutMapping("/member")
	ApiResponse<Void> updateMember(@RequestHeader Long memberId,
		@RequestBody UpdateMemberRequest updateMemberRequest);

}

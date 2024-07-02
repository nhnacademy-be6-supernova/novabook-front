package store.novabook.front.api.member.member.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import store.novabook.front.api.coupon.dto.request.CreateMemberCouponRequest;
import store.novabook.front.api.member.member.dto.request.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.request.DeleteMemberRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberPasswordRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberRequest;
import store.novabook.front.api.member.member.dto.response.CreateMemberCouponResponse;
import store.novabook.front.api.member.member.dto.response.CreateMemberResponse;
import store.novabook.front.api.member.member.dto.response.GetMemberResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "memberClient")
public interface MemberClient {

	@PostMapping
	ApiResponse<CreateMemberResponse> createMember(@RequestBody CreateMemberRequest createMemberRequest);

	@GetMapping("/member")
	ApiResponse<GetMemberResponse> getMember(@RequestHeader(required = false) Long memberId);

	@PutMapping("/member/update")
	ApiResponse<Void> updateMember(@RequestHeader(required = false) Long memberId,
		@RequestBody UpdateMemberRequest updateMemberRequest);

	@PutMapping("/member/password")
	ApiResponse<Void> updateMemberPassword(@RequestHeader(required = false) Long memberId,
		@RequestBody UpdateMemberPasswordRequest updateMemberPasswordRequest);

	@PutMapping("/member/withdraw")
	ApiResponse<Void> updateMemberStatusToWithdraw(@RequestHeader(required = false) Long memberId, @RequestBody DeleteMemberRequest deleteMemberRequest);

	@PostMapping("/coupons")
	ApiResponse<CreateMemberCouponResponse> createMemberCoupon(@RequestHeader Long memberId, @RequestBody CreateMemberCouponRequest request);
}

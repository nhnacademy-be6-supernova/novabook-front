package store.novabook.front.api.member.address;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import store.novabook.front.api.member.address.dto.request.UpdateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.response.CreateMemberAddressResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressListResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "memberAddressClient")
public interface MemberAddressClient {

	@PostMapping
	ApiResponse<CreateMemberAddressResponse> createMemberAddress(
		@RequestBody CreateMemberAddressRequest createMemberAddressRequest, @RequestHeader Long memberId);

	@GetMapping
	ApiResponse<GetMemberAddressListResponse> getMemberAddressList(@RequestHeader Long memberId);

	@PutMapping("/{memberAddressId}")
	ApiResponse<Void> updateMemberAddress(@PathVariable Long memberAddressId,
		@RequestBody UpdateMemberAddressRequest updateMemberAddressRequest);

	@DeleteMapping("/{memberAddressId}")
	ApiResponse<Void> deleteMemberAddress(@PathVariable Long memberAddressId);
}

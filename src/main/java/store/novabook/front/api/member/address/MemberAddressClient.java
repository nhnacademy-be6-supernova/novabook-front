package store.novabook.front.api.member.address;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import store.novabook.front.api.ApiResponse;
import store.novabook.front.api.member.address.dto.CreateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.CreateMemberAddressResponse;
import store.novabook.front.api.member.address.dto.GetMemberAddressListResponse;
import store.novabook.front.api.member.address.dto.UpdateMemberAddressRequest;

@FeignClient(name = "memberAddressClient", url = "http://localhost:8090/api/v1/store/addresses")
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

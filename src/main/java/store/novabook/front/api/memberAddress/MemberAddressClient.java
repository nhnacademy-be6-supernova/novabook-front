package store.novabook.front.api.memberAddress;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import store.novabook.front.api.ApiResponse;
import store.novabook.front.api.memberAddress.dto.CreateMemberAddressRequest;
import store.novabook.front.api.memberAddress.dto.CreateMemberAddressResponse;

@FeignClient(name = "memberAddressClient", url = "http://localhost:8090/api/v1/store/addresses")
public interface    MemberAddressClient {

	@PostMapping
	ApiResponse<CreateMemberAddressResponse> createMemberAddress(@RequestBody CreateMemberAddressRequest createMemberAddressRequest, @RequestHeader Long memberId);
}

package com.nhnacademy.novabook_front.api.memberAddress;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.novabook_front.api.ApiResponse;
import com.nhnacademy.novabook_front.api.memberAddress.dto.CreateMemberAddressRequest;
import com.nhnacademy.novabook_front.api.memberAddress.dto.CreateMemberAddressResponse;

@FeignClient(name = "memberAddressClient", url = "http://localhost:8090/api/v1/store/addresses")
public interface MemberAddressClient {

	@PostMapping
	ApiResponse<CreateMemberAddressResponse> createMemberAddress(@RequestBody CreateMemberAddressRequest createMemberAddressRequest);
}

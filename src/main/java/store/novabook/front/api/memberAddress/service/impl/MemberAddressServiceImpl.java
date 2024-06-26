package store.novabook.front.api.memberAddress.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.api.memberAddress.MemberAddressClient;
import store.novabook.front.api.memberAddress.dto.CreateMemberAddressRequest;
import store.novabook.front.api.memberAddress.dto.CreateMemberAddressResponse;
import store.novabook.front.api.memberAddress.service.MemberAddressService;

@Service
@RequiredArgsConstructor
public class MemberAddressServiceImpl implements MemberAddressService {
	private final MemberAddressClient memberAddressClient;

	public CreateMemberAddressResponse createMemberAddress(CreateMemberAddressRequest createMemberAddressRequest) {
		ApiResponse<CreateMemberAddressResponse> memberAddress = memberAddressClient.createMemberAddress(
			createMemberAddressRequest);
		return memberAddress.getBody();
	}
}

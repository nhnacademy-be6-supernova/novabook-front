package store.novabook.front.api.member.address.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.address.dto.request.CreateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.request.UpdateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.response.CreateMemberAddressResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressListResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressResponse;
import store.novabook.front.api.member.address.service.MemberAddressClient;
import store.novabook.front.api.member.address.service.MemberAddressService;
import store.novabook.front.common.response.ApiResponse;

@Service
@RequiredArgsConstructor
public class MemberAddressServiceImpl implements MemberAddressService {

	private final MemberAddressClient memberAddressClient;

	@Override
	public CreateMemberAddressResponse createMemberAddress(CreateMemberAddressRequest createMemberAddressRequest) {
		ApiResponse<CreateMemberAddressResponse> memberAddress = memberAddressClient.createMemberAddress(
			createMemberAddressRequest);
		return memberAddress.getBody();
	}

	@Override
	public List<GetMemberAddressResponse> getMemberAddressAll() {
		ApiResponse<GetMemberAddressListResponse> response = memberAddressClient.getMemberAddressAll();
		return response.getBody().memberAddresses();
	}

	@Override
	public void updateMemberAddress(Long memberAddressId, UpdateMemberAddressRequest updateMemberAddressRequest) {
		memberAddressClient.updateMemberAddress(memberAddressId, updateMemberAddressRequest);
	}

	@Override
	public void deleteMemberAddress(Long memberAddressId) {
		memberAddressClient.deleteMemberAddress(memberAddressId);
	}
}

package store.novabook.front.api.member.address.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.ApiResponse;
import store.novabook.front.api.member.address.MemberAddressClient;
import store.novabook.front.api.member.address.dto.CreateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.CreateMemberAddressResponse;
import store.novabook.front.api.member.address.dto.GetMemberAddressListResponse;
import store.novabook.front.api.member.address.dto.GetMemberAddressResponse;
import store.novabook.front.api.member.address.dto.UpdateMemberAddressRequest;
import store.novabook.front.api.member.address.service.MemberAddressService;

@Service
@RequiredArgsConstructor
public class MemberAddressServiceImpl implements MemberAddressService {
	private final MemberAddressClient memberAddressClient;

	@Override
	public CreateMemberAddressResponse createMemberAddress(CreateMemberAddressRequest createMemberAddressRequest, Long memberId) {
		ApiResponse<CreateMemberAddressResponse> memberAddress = memberAddressClient.createMemberAddress(
			createMemberAddressRequest, memberId);
		return memberAddress.getBody();
	}

	@Override
	public List<GetMemberAddressResponse> getMemberAddresses(Long memberId) {
		ApiResponse<GetMemberAddressListResponse> response = memberAddressClient.getMemberAddressList(memberId);
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

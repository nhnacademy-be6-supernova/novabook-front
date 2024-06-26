package store.novabook.front.api.memberAddress.service;

import java.util.List;

import store.novabook.front.api.memberAddress.dto.CreateMemberAddressRequest;
import store.novabook.front.api.memberAddress.dto.CreateMemberAddressResponse;
import store.novabook.front.api.memberAddress.dto.GetMemberAddressResponse;
import store.novabook.front.api.memberAddress.dto.UpdateMemberAddressRequest;

public interface MemberAddressService {
	CreateMemberAddressResponse createMemberAddress(CreateMemberAddressRequest createMemberAddressRequest, Long memberId);

	List<GetMemberAddressResponse> getMemberAddresses(Long memberId);

	void updateMemberAddress(Long memberAddressId, UpdateMemberAddressRequest updateMemberAddressRequest);

	void deleteMemberAddress(Long memberAddressId);
}

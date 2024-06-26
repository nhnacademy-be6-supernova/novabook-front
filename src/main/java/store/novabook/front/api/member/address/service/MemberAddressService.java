package store.novabook.front.api.member.address.service;

import java.util.List;

import store.novabook.front.api.member.address.dto.CreateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.CreateMemberAddressResponse;
import store.novabook.front.api.member.address.dto.GetMemberAddressResponse;
import store.novabook.front.api.member.address.dto.UpdateMemberAddressRequest;

public interface MemberAddressService {
	CreateMemberAddressResponse createMemberAddress(CreateMemberAddressRequest createMemberAddressRequest, Long memberId);

	List<GetMemberAddressResponse> getMemberAddresses(Long memberId);

	void updateMemberAddress(Long memberAddressId, UpdateMemberAddressRequest updateMemberAddressRequest);

	void deleteMemberAddress(Long memberAddressId);
}

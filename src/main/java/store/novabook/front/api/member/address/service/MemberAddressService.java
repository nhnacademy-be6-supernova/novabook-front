package store.novabook.front.api.member.address.service;

import java.util.List;

import store.novabook.front.api.member.address.dto.request.CreateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.request.UpdateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.response.CreateMemberAddressResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressResponse;

public interface MemberAddressService {

	CreateMemberAddressResponse createMemberAddress(CreateMemberAddressRequest createMemberAddressRequest,
		Long memberId);

	List<GetMemberAddressResponse> getMemberAddresses(Long memberId);

	void updateMemberAddress(Long memberAddressId, UpdateMemberAddressRequest updateMemberAddressRequest);

	void deleteMemberAddress(Long memberAddressId);
}

package store.novabook.front.api.member.address.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record GetMemberAddressListResponse(
	List<GetMemberAddressResponse> memberAddresses
) {
}

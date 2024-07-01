package store.novabook.front.api.member.address.dto.response;

import java.util.List;

import lombok.Builder;
import store.novabook.front.api.member.address.dto.GetMemberAddressResponse;

@Builder
public record GetMemberAddressListResponse(
	List<GetMemberAddressResponse> memberAddresses
) {
}

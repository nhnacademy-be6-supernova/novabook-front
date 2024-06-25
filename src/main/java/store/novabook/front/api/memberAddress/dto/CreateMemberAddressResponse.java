package store.novabook.front.api.memberAddress.dto;

import lombok.Builder;
import store.novabook.front.api.memberAddress.domain.StreetAddress;

@Builder
public record CreateMemberAddressResponse(Long id, StreetAddress streetAddress, String memberAddressDetail) {
}

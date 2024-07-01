package store.novabook.front.api.member.address.dto.response;

import lombok.Builder;
import store.novabook.front.api.member.address.domain.StreetAddress;

@Builder
public record CreateMemberAddressResponse(Long id, StreetAddress streetAddress, String memberAddressDetail) {
}

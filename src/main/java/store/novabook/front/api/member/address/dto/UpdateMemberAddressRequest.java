package store.novabook.front.api.member.address.dto;

import lombok.Builder;

@Builder
public record UpdateMemberAddressRequest(

	String nickname,

	String zipcode,

	String streetAddress,

	String memberAddressDetail

) {
}

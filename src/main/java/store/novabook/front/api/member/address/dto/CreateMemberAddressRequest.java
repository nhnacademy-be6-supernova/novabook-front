package store.novabook.front.api.member.address.dto;

import lombok.Builder;

@Builder
public record CreateMemberAddressRequest(
	String zipcode,
	String streetAddress,
	String nickname,
	String memberAddressDetail
) {

}

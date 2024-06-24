package store.novabook.front.api.memberAddress.dto;

import lombok.Builder;

@Builder
public record CreateMemberAddressRequest(
	String zipcode,
	String streetAddress,
	String nickname,
	String memberAddressDetail
) {

}

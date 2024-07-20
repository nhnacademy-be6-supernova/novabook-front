package store.novabook.front.api.member.address.dto.response;

import lombok.Builder;

@Builder
public record GetMemberAddressResponse(
	Long id,
	Long streetAddressesId,
	Long memberId,
	String zipcode,
	String nickname,
	String streetAddresses,
	String memberAddressDetail
) {

}

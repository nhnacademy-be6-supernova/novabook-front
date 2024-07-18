package store.novabook.front.api.member.address.dto.response;

import lombok.Builder;

@Builder
public record GetMemberAddressResponse(
	Long id,
	Long streetAddressId,
	Long memberId,
	String zipcode,
	String nickname,
	String streetAddress,
	String memberAddressDetail
) {

}

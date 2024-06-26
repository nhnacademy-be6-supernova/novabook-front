package store.novabook.front.api.member.address.dto;

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

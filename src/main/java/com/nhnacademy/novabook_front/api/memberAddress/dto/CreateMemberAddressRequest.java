package com.nhnacademy.novabook_front.api.memberAddress.dto;

import lombok.Builder;

@Builder
public record CreateMemberAddressRequest(
	String zipcode,
	Long memberId,
	String streetAddress,
	String nickname,
	String memberAddressDetail
) {

}

package com.nhnacademy.novabook_front.api.memberAddress.dto;

import com.nhnacademy.novabook_front.api.memberAddress.domain.StreetAddress;

import lombok.Builder;

@Builder
public record CreateMemberAddressResponse(Long id, StreetAddress streetAddress, String memberAddressDetail) {
}

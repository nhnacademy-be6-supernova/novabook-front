package com.nhnacademy.novabook_front.api.member.dto;

import lombok.Builder;

@Builder
public record CheckDuplicateLoginIdResponse(
	String loginId
) {
}

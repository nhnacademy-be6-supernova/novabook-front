package com.nhnacademy.novabook_front.api.member.dto;

import com.nhnacademy.novabook_front.api.member.domain.Member;

import lombok.Builder;

@Builder
public record CreateMemberResponse(Long id) {
	public static CreateMemberResponse fromEntity(Member member) {
		return CreateMemberResponse.builder()
			.id(member.getId())
			.build();
	}
}

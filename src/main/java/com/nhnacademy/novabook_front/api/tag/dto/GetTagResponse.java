package com.nhnacademy.novabook_front.api.tag.dto;

import com.nhnacademy.novabook_front.api.tag.domain.Tag;

import lombok.Builder;

@Builder
public record GetTagResponse(Long id, String name) {
	public static GetTagResponse fromEntity(Tag tag) {
		return GetTagResponse.builder().name(tag.getName()).id(tag.getId()).build();
	}
}

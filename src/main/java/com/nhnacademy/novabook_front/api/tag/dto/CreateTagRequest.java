package com.nhnacademy.novabook_front.api.tag.dto;

import lombok.Builder;

@Builder
public record CreateTagRequest(
	String name) {
}

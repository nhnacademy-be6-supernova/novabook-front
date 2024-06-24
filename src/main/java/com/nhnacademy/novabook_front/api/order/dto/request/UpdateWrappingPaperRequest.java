package com.nhnacademy.novabook_front.api.order.dto.request;

import lombok.Builder;

@Builder
public record UpdateWrappingPaperRequest(
	Long price,
	String name,
	String status
) {
}
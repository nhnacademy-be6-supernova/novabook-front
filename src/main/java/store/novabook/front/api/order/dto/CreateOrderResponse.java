package com.nhnacademy.novabook_front.api.order.dto;

import lombok.Builder;

@Builder
public record CreateOrderResponse(Long id) {
	public static CreateOrderResponse fromEntity(Long id) {
		return CreateOrderResponse.builder()
			.id(id)
			.build();
	}
}

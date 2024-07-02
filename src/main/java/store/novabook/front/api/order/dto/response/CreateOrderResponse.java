package store.novabook.front.api.order.dto.response;

import lombok.Builder;

@Builder
public record CreateOrderResponse(Long id) {
	public static CreateOrderResponse fromEntity(Long id) {
		return CreateOrderResponse.builder()
			.id(id)
			.build();
	}
}

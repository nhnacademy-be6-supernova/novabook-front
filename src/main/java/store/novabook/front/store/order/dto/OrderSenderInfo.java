package store.novabook.front.store.order.dto;

import lombok.Builder;

@Builder
public record OrderSenderInfo(
	String name,
	String phone
	) {
}

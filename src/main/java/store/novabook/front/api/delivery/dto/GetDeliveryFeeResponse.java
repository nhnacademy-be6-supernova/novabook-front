package store.novabook.front.api.delivery.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record GetDeliveryFeeResponse(
	Long id,
	Long fee,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
}

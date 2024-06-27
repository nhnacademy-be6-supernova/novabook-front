package store.novabook.front.api.order.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record GetWrappingPaperResponse(
	Long id,
	long price,
	String name,
	String status,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
}

package store.novabook.front.store.order.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record GetOrdersBookResponse(
	Long id,
	Long ordersId,
	Long bookId,
	String bookName,
	int quantity,
	long price,
	String orderStatus,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
}

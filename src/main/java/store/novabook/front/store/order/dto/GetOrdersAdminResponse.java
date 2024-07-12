package store.novabook.front.store.order.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record GetOrdersAdminResponse(
	Long ordersId,
	String memberLoginId,
	Long ordersStatusId,
	LocalDateTime ordersDate,
	// Long discountAmount,
	Long totalAmount,
	LocalDateTime createdAt
) {
}
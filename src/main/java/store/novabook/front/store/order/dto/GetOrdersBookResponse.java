package store.novabook.front.store.order.dto;

import java.time.LocalDateTime;

public record GetOrdersBookResponse(Long ordersId,
									String firstBookTitle,
									Long extraBookCount,
									Long totalAmount,
									String orderStatus,
									LocalDateTime createdAt) {
}

package store.novabook.front.store.order.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record GetOrdersResponse(
	String code,
	Long memberId,
	Long deliveryFeeId,
	Long wrappingPaperId,
	Long ordersStatusId,
	LocalDateTime ordersDate,
	Long totalAmount,
	LocalDateTime deliveryDate,
	long bookPurchaseAmount,
	String deliveryAddress,
	Long couponId,
	Long usePointAmount,
	Long pointSaveAmount,
	String paymentKey,
	String receiverName,
	String receiverNumber,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
}

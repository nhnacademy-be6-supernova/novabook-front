package store.novabook.front.store.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record GetOrderDetailResponse(
	Long ordersId,
	Long ordersStatusId,
	String ordersStatusName,
	List<String> bookTitle,
	Integer quantity,
	Long deliveryFee,
	Long wrappingFee,
	String receiverName,
	String receiverNumber,
	String receiverAddress,
	LocalDateTime expectedDeliveryDate,
	Long totalPrice,
	Long couponDiscountAmount,
	Long finalAmount,
	Long pointSaveAmount
) {
}

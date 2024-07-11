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
	Long price,
	Integer quantity,
	Long deliveryFee,
	Long wrappingFee,
	String receiverName,
	String receiverNumber,
	String receiverAddress,
	LocalDateTime expectedDeliveryDate,
	Long totalPrice
	// Long couponDiscountPrice,
	// Long finalPrice,
	// Long pointsSave
) {
}

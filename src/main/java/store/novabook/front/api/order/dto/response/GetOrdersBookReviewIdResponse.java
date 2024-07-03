package store.novabook.front.api.order.dto.response;

import java.time.LocalDateTime;

public record GetOrdersBookReviewIdResponse(
	Long ordersBookId,
	Long reviewId,
	Long ordersId,
	Long bookId,
	String bookTitle,
	LocalDateTime orderAt
) {
}

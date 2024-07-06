package store.novabook.front.api.cart.dto.response;

import lombok.Builder;

@Builder
public record GetCartBookResponse(
	Long bookId,
	String image,
	String title,
	Long price,
	Long discountPrice,
	Integer quantity
) {
}

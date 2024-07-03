package store.novabook.front.api.cart.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record GetCartResponse(
	Long cartId,
	List<GetCartBookResponse> cartBookList
	) {
}

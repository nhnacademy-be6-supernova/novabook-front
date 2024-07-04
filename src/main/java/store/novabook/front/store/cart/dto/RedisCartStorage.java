package store.novabook.front.store.cart.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import store.novabook.front.api.cart.dto.response.GetCartBookResponse;
import store.novabook.front.api.cart.dto.response.GetCartResponse;

@RedisHash("cart")
@Builder
public record RedisCartStorage(
	@Id
	Long cartId,
	Map<Long, Integer> books
) {
	public static RedisCartStorage of(GetCartResponse getCartResponse) {
		return RedisCartStorage.builder()
			.cartId(getCartResponse.cartId())
			.books(getBooks(getCartResponse))
			.build();

	}
	public static Map<Long, Integer> getBooks(GetCartResponse GetCartResponse) {
		HashMap<Long, Integer> books = new HashMap<>();

		for (GetCartBookResponse getCartBookResponse : GetCartResponse.cartBookList()) {
			books.put(getCartBookResponse.cartBookId(), getCartBookResponse.quantity());
		}

		return books;
	}
}
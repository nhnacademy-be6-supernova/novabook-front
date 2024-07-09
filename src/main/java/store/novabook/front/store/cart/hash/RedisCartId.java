package store.novabook.front.store.cart.hash;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;

@RedisHash("cartId")
@Builder
public record RedisCartId(
	@Id
	Object userId,
	Long cartId
) {
	public static RedisCartId of(Object userId, Long cartId) {
		return RedisCartId.builder()
			.userId(userId)
			.cartId(cartId)
			.build();

	}
}
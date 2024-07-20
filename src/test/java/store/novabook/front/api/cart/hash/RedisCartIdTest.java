package store.novabook.front.api.cart.hash;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import store.novabook.front.store.cart.hash.RedisCartId;

class RedisCartIdTest {

	@Test
	void testBuilder() {
		Object userId = 1L;
		Long cartId = 123L;

		RedisCartId redisCartId = RedisCartId.builder()
			.userId(userId)
			.cartId(cartId)
			.build();

		assertEquals(userId, redisCartId.userId());
		assertEquals(cartId, redisCartId.cartId());
	}

	@Test
	void testOf() {
		Object userId = 1L;
		Long cartId = 123L;

		RedisCartId redisCartId = RedisCartId.of(userId, cartId);

		assertEquals(userId, redisCartId.userId());
		assertEquals(cartId, redisCartId.cartId());
		assertNotNull(redisCartId);
	}
}

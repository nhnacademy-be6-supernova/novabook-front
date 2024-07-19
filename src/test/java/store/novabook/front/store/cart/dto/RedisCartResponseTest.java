package store.novabook.front.store.cart.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class RedisCartResponseTest {

	@Test
	void testRedisCartResponse() {
		Boolean success = true;
		String message = "Operation successful";

		RedisCartResponse response = new RedisCartResponse(success, message);

		assertNotNull(response);
		assertEquals(success, response.Success());
		assertEquals(message, response.message());
	}

	@Test
	void testEmptyMessage() {
		Boolean success = false;
		String message = "";

		RedisCartResponse response = new RedisCartResponse(success, message);

		assertNotNull(response);
		assertEquals(success, response.Success());
		assertEquals(message, response.message());
	}
}

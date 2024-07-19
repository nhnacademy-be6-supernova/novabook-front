package store.novabook.front.api.cart.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import store.novabook.front.api.cart.dto.CartBookDTO;
import store.novabook.front.api.cart.dto.CartBookListDTO;
import store.novabook.front.store.cart.hash.RedisCartHash;

class RedisCartHashTest {

	@Test
	void testBuilder() {
		Object cartId = 1L;
		List<CartBookDTO> cartBookList = List.of(new CartBookDTO(1L, "Title", "image.jpg", 100L, 10L, 1, true, 1L));

		RedisCartHash redisCartHash = RedisCartHash.builder()
			.cartId(cartId)
			.cartBookList(cartBookList)
			.build();

		assertEquals(cartId, redisCartHash.getCartId());
		assertEquals(cartBookList, redisCartHash.getCartBookList());
	}

	@Test
	void testOfWithCartId() {
		Object cartId = 1L;

		RedisCartHash redisCartHash = RedisCartHash.of(cartId);

		assertEquals(cartId, redisCartHash.getCartId());
		assertNotNull(redisCartHash.getCartBookList());
		assertEquals(Collections.emptyList(), redisCartHash.getCartBookList());
	}

	@Test
	void testOfWithCartBookDTO() {
		Object cartId = 1L;
		CartBookDTO cartBookDTO = new CartBookDTO(1L, "Title", "image.jpg", 100L, 10L, 1, true, 1L);

		RedisCartHash redisCartHash = RedisCartHash.of(cartId, cartBookDTO);

		assertEquals(cartId, redisCartHash.getCartId());
		assertEquals(List.of(cartBookDTO), redisCartHash.getCartBookList());
	}

	@Test
	void testOfWithCartBookListDTO() {
		Object cartId = 1L;
		CartBookDTO cartBookDTO1 = new CartBookDTO(1L, "Title1", "image1.jpg", 100L, 10L, 1, true, 1L);
		CartBookDTO cartBookDTO2 = new CartBookDTO(2L, "Title2", "image2.jpg", 200L, 20L, 2, false, 2L);
		CartBookListDTO cartBookListDTO = new CartBookListDTO(List.of(cartBookDTO1, cartBookDTO2));

		RedisCartHash redisCartHash = RedisCartHash.of(cartId, cartBookListDTO);

		assertEquals(cartId, redisCartHash.getCartId());
		assertEquals(cartBookListDTO.getCartBookList(), redisCartHash.getCartBookList());
	}

	@Test
	void testUpdate() {
		Object cartId = 1L;
		CartBookDTO cartBookDTO1 = new CartBookDTO(1L, "Title1", "image1.jpg", 100L, 10L, 1, true, 1L);
		CartBookDTO cartBookDTO2 = new CartBookDTO(2L, "Title2", "image2.jpg", 200L, 20L, 2, false, 2L);
		RedisCartHash redisCartHash = RedisCartHash.of(cartId, cartBookDTO1);

		redisCartHash.update(List.of(cartBookDTO1, cartBookDTO2));

		assertEquals(List.of(cartBookDTO1, cartBookDTO2), redisCartHash.getCartBookList());
	}
}

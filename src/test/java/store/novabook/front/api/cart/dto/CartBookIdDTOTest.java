package store.novabook.front.api.cart.dto;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

class CartBookIdDTOTest {

	@Test
	void testCartBookIdDTOBuilder() {
		Map<Long, Integer> bookIdsAndQuantity = Map.of(
			1L, 2,
			2L, 3
		);

		CartBookIdDTO cartBookIdDTO = new CartBookIdDTO(bookIdsAndQuantity);

		assertThat(cartBookIdDTO).isNotNull();
		assertThat(cartBookIdDTO.bookIdsAndQuantity()).isEqualTo(bookIdsAndQuantity);
	}

	@Test
	void testCartBookIdDTOWithNullMap() {
		try {
			new CartBookIdDTO(null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("bookIdsAndQuantity");
		}
	}
}

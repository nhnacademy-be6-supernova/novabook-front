package store.novabook.front.api.cart.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class CartBookIdDTOTest {

	@Test
	public void testCartBookIdDTOBuilder() {
		// Arrange
		Map<Long, Integer> bookIdsAndQuantity = Map.of(
			1L, 2,
			2L, 3
		);

		// Act
		CartBookIdDTO cartBookIdDTO = new CartBookIdDTO(bookIdsAndQuantity);

		// Assert
		assertThat(cartBookIdDTO).isNotNull();
		assertThat(cartBookIdDTO.bookIdsAndQuantity()).isEqualTo(bookIdsAndQuantity);
	}

	@Test
	public void testCartBookIdDTOWithNullMap() {
		// Act & Assert
		try {
			new CartBookIdDTO(null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("bookIdsAndQuantity");
		}
	}
}

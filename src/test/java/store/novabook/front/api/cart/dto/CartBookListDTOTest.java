package store.novabook.front.api.cart.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CartBookListDTOTest {

	@Test
	public void testCartBookListDTOBuilder() {
		// Arrange
		List<CartBookDTO> cartBookList = List.of(
			CartBookDTO.builder()
				.bookId(1L)
				.title("Book Title 1")
				.image("image1.jpg")
				.price(1000L)
				.discountPrice(800L)
				.quantity(1)
				.isPackaged(true)
				.bookStatusId(1L)
				.build(),
			CartBookDTO.builder()
				.bookId(2L)
				.title("Book Title 2")
				.image("image2.jpg")
				.price(2000L)
				.discountPrice(1500L)
				.quantity(2)
				.isPackaged(false)
				.bookStatusId(2L)
				.build()
		);

		// Act
		CartBookListDTO cartBookListDTO = new CartBookListDTO(cartBookList);

		// Assert
		assertThat(cartBookListDTO).isNotNull();
		assertThat(cartBookListDTO.getCartBookList()).isEqualTo(cartBookList);
	}

	@Test
	public void testCartBookListDTOWithNullList() {
		// Act & Assert
		try {
			new CartBookListDTO(null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("cartBookList");
		}
	}

	@Test
	public void testCartBookListDTOWithEmptyList() {
		// Arrange
		List<CartBookDTO> emptyCartBookList = Collections.emptyList();

		// Act
		CartBookListDTO cartBookListDTO = new CartBookListDTO(emptyCartBookList);

		// Assert
		assertThat(cartBookListDTO).isNotNull();
		assertThat(cartBookListDTO.getCartBookList()).isEmpty();
	}
}

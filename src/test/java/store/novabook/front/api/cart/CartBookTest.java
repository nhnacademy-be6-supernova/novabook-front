package store.novabook.front.api.cart;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class CartBookTest {

	@Test
	void testCartBookCreation() {
		Long id = 1L;
		Long cartId = 100L;
		Long bookId = 200L;
		int quantity = 3;
		boolean isExposed = true;
		LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
		LocalDateTime updatedAt = LocalDateTime.now();

		CartBook cartBook = new CartBook();
		setField(cartBook, "id", id);
		setField(cartBook, "cartId", cartId);
		setField(cartBook, "bookId", bookId);
		setField(cartBook, "quantity", quantity);
		setField(cartBook, "isExposed", isExposed);
		setField(cartBook, "createdAt", createdAt);
		setField(cartBook, "updatedAt", updatedAt);

		assertThat(cartBook.getId()).isEqualTo(id);
		assertThat(cartBook.getCartId()).isEqualTo(cartId);
		assertThat(cartBook.getBookId()).isEqualTo(bookId);
		assertThat(cartBook.getQuantity()).isEqualTo(quantity);
		assertThat(cartBook.isExposed()).isEqualTo(isExposed);
		assertThat(cartBook.getCreatedAt()).isEqualTo(createdAt);
		assertThat(cartBook.getUpdatedAt()).isEqualTo(updatedAt);
	}

	private void setField(CartBook cartBook, String fieldName, Object value) {
		try {
			var field = CartBook.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(cartBook, value);
		} catch (Exception e) {
			throw new RuntimeException("Failed to set field value", e);
		}
	}
}

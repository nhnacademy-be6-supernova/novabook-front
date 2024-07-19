package store.novabook.front.api.cart.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class UpdateCartBookQuantityRequestTest {

	@Test
	public void testUpdateCartBookQuantityRequestWithValidData() {
		// Arrange
		Long bookId = 1L;
		Integer quantity = 5;

		// Act
		UpdateCartBookQuantityRequest request = new UpdateCartBookQuantityRequest(bookId, quantity);

		// Assert
		assertThat(request).isNotNull();
		assertThat(request.bookId()).isEqualTo(bookId);
		assertThat(request.quantity()).isEqualTo(quantity);
	}

	@Test
	public void testUpdateCartBookQuantityRequestWithNullBookId() {
		// Act & Assert
		try {
			new UpdateCartBookQuantityRequest(null, 5);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("bookId");
		}
	}

	@Test
	public void testUpdateCartBookQuantityRequestWithNullQuantity() {
		// Act & Assert
		try {
			new UpdateCartBookQuantityRequest(1L, null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("quantity");
		}
	}
}

package store.novabook.front.api.cart.dto.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UpdateCartBookQuantityRequestTest {

	@Test
	void testUpdateCartBookQuantityRequestWithValidData() {
		Long bookId = 1L;
		Integer quantity = 5;

		UpdateCartBookQuantityRequest request = new UpdateCartBookQuantityRequest(bookId, quantity);

		assertThat(request).isNotNull();
		assertThat(request.bookId()).isEqualTo(bookId);
		assertThat(request.quantity()).isEqualTo(quantity);
	}

	@Test
	void testUpdateCartBookQuantityRequestWithNullBookId() {
		try {
			new UpdateCartBookQuantityRequest(null, 5);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("bookId");
		}
	}

	@Test
	void testUpdateCartBookQuantityRequestWithNullQuantity() {
		// Act & Assert
		try {
			new UpdateCartBookQuantityRequest(1L, null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("quantity");
		}
	}
}

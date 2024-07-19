package store.novabook.front.api.cart.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

public class DeleteCartBookListRequestTest {

	@Test
	public void testDeleteCartBookListRequestWithValidData() {
		// Arrange
		List<Long> bookIds = List.of(1L, 2L, 3L);

		// Act
		DeleteCartBookListRequest request = new DeleteCartBookListRequest(bookIds);

		// Assert
		assertThat(request).isNotNull();
		assertThat(request.bookIds()).isEqualTo(bookIds);
	}

	@Test
	public void testDeleteCartBookListRequestWithNullBookIds() {
		// Act & Assert
		try {
			new DeleteCartBookListRequest(null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("bookIds");
		}
	}

	@Test
	public void testDeleteCartBookListRequestWithEmptyBookIds() {
		// Arrange
		List<Long> emptyBookIds = List.of();

		// Act
		DeleteCartBookListRequest request = new DeleteCartBookListRequest(emptyBookIds);

		// Assert
		assertThat(request).isNotNull();
		assertThat(request.bookIds()).isEmpty();
	}
}


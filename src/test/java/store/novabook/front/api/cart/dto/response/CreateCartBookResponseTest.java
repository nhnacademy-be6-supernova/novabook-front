package store.novabook.front.api.cart.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CreateCartBookResponseTest {

	@Test
	public void testCreateCartBookResponseWithValidData() {
		// Arrange
		List<Long> ids = Arrays.asList(1L, 2L, 3L);

		// Act
		CreateCartBookResponse response = new CreateCartBookResponse(ids);

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.ids()).isEqualTo(ids);
	}

	@Test
	public void testCreateCartBookResponseWithEmptyList() {
		// Arrange
		List<Long> ids = Collections.emptyList();

		// Act
		CreateCartBookResponse response = new CreateCartBookResponse(ids);

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.ids()).isEmpty();
	}

	@Test
	public void testCreateCartBookResponseWithNullIds() {
		// Act & Assert
		try {
			new CreateCartBookResponse(null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("ids");
		}
	}
}

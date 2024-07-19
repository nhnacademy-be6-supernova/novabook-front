package store.novabook.front.api.cart.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CreateCartBookListResponseTest {

	@Test
	public void testCreateCartBookListResponseWithValidData() {
		// Arrange
		List<Long> ids = Arrays.asList(1L, 2L, 3L);

		// Act
		CreateCartBookListResponse response = new CreateCartBookListResponse(ids);

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.ids()).isEqualTo(ids);
	}

	@Test
	public void testCreateCartBookListResponseWithEmptyList() {
		// Arrange
		List<Long> ids = Collections.emptyList();

		// Act
		CreateCartBookListResponse response = new CreateCartBookListResponse(ids);

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.ids()).isEmpty();
	}

	@Test
	public void testCreateCartBookListResponseWithNullIds() {
		// Act & Assert
		try {
			new CreateCartBookListResponse(null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("ids");
		}
	}
}

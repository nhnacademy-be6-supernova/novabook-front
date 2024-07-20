package store.novabook.front.api.cart.dto.response;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class CreateCartBookResponseTest {

	@Test
	void testCreateCartBookResponseWithValidData() {
		List<Long> ids = Arrays.asList(1L, 2L, 3L);

		CreateCartBookResponse response = new CreateCartBookResponse(ids);

		assertThat(response).isNotNull();
		assertThat(response.ids()).isEqualTo(ids);
	}

	@Test
	void testCreateCartBookResponseWithEmptyList() {
		List<Long> ids = Collections.emptyList();

		CreateCartBookResponse response = new CreateCartBookResponse(ids);

		assertThat(response).isNotNull();
		assertThat(response.ids()).isEmpty();
	}

	@Test
	void testCreateCartBookResponseWithNullIds() {
		try {
			new CreateCartBookResponse(null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("ids");
		}
	}
}

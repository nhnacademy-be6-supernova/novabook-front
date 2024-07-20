package store.novabook.front.api.cart.dto.response;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class CreateCartBookListResponseTest {

	@Test
	void testCreateCartBookListResponseWithValidData() {
		List<Long> ids = Arrays.asList(1L, 2L, 3L);

		CreateCartBookListResponse response = new CreateCartBookListResponse(ids);

		assertThat(response).isNotNull();
		assertThat(response.ids()).isEqualTo(ids);
	}

	@Test
	void testCreateCartBookListResponseWithEmptyList() {
		List<Long> ids = Collections.emptyList();

		CreateCartBookListResponse response = new CreateCartBookListResponse(ids);

		assertThat(response).isNotNull();
		assertThat(response.ids()).isEmpty();
	}

	@Test
	void testCreateCartBookListResponseWithNullIds() {
		try {
			new CreateCartBookListResponse(null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("ids");
		}
	}
}

package store.novabook.front.api.cart.dto.request;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class DeleteCartBookListRequestTest {

	@Test
	void testDeleteCartBookListRequestWithValidData() {
		List<Long> bookIds = List.of(1L, 2L, 3L);

		DeleteCartBookListRequest request = new DeleteCartBookListRequest(bookIds);

		assertThat(request).isNotNull();
		assertThat(request.bookIds()).isEqualTo(bookIds);
	}

	@Test
	void testDeleteCartBookListRequestWithNullBookIds() {
		try {
			new DeleteCartBookListRequest(null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class)
				.hasMessageContaining("bookIds");
		}
	}

	@Test
	void testDeleteCartBookListRequestWithEmptyBookIds() {
		List<Long> emptyBookIds = List.of();

		DeleteCartBookListRequest request = new DeleteCartBookListRequest(emptyBookIds);

		assertThat(request).isNotNull();
		assertThat(request.bookIds()).isEmpty();
	}
}


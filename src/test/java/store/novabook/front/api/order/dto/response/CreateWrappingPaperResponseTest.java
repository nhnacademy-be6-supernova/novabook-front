package store.novabook.front.api.order.dto.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CreateWrappingPaperResponseTest {

	@Test
	void testCreateWrappingPaperResponse() {
		Long expectedId = 789L;

		CreateWrappingPaperResponse response = new CreateWrappingPaperResponse(expectedId);

		assertThat(response).isNotNull();
		assertThat(response.id()).isEqualTo(expectedId);
	}
}

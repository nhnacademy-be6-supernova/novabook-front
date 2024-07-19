package store.novabook.front.api.order.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CreateWrappingPaperResponseTest {

	@Test
	public void testCreateWrappingPaperResponse() {
		// Arrange
		Long expectedId = 789L;

		// Act
		CreateWrappingPaperResponse response = new CreateWrappingPaperResponse(expectedId);

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.id()).isEqualTo(expectedId);
	}
}

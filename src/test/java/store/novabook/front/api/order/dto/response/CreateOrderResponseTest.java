package store.novabook.front.api.order.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CreateOrderResponseTest {

	@Test
	public void testBuilder() {
		// Arrange
		Long expectedId = 123L;

		// Act
		CreateOrderResponse response = CreateOrderResponse.builder()
			.id(expectedId)
			.build();

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.id()).isEqualTo(expectedId);
	}

	@Test
	public void testFromEntity() {
		// Arrange
		Long expectedId = 456L;

		// Act
		CreateOrderResponse response = CreateOrderResponse.fromEntity(expectedId);

		// Assert
		assertThat(response).isNotNull();
		assertThat(response.id()).isEqualTo(expectedId);
	}
}

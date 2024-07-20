package store.novabook.front.api.order.dto.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CreateOrderResponseTest {

	@Test
	void testBuilder() {
		Long expectedId = 123L;

		CreateOrderResponse response = CreateOrderResponse.builder()
			.id(expectedId)
			.build();

		assertThat(response).isNotNull();
		assertThat(response.id()).isEqualTo(expectedId);
	}

	@Test
	void testFromEntity() {
		Long expectedId = 456L;

		CreateOrderResponse response = CreateOrderResponse.fromEntity(expectedId);

		assertThat(response).isNotNull();
		assertThat(response.id()).isEqualTo(expectedId);
	}
}

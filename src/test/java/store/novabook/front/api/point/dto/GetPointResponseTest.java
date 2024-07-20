package store.novabook.front.api.point.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import store.novabook.front.api.point.dto.response.GetPointResponse;

class GetPointResponseTest {

	@Test
	void testGetPointResponse_Valid() {
		Long pointAmount = 1000L;

		GetPointResponse response = GetPointResponse.builder()
			.pointAmount(pointAmount)
			.build();

		assertNotNull(response);
		assertEquals(pointAmount, response.pointAmount());
	}

	@Test
	void testGetPointResponse_NullPointAmount() {
		GetPointResponse response = GetPointResponse.builder()
			.pointAmount(null)
			.build();

		assertNotNull(response);
		assertNull(response.pointAmount());
	}

	@Test
	void testGetPointResponse_ZeroPointAmount() {
		Long pointAmount = 0L;

		GetPointResponse response = GetPointResponse.builder()
			.pointAmount(pointAmount)
			.build();

		assertNotNull(response);
		assertEquals(pointAmount, response.pointAmount());
	}

	@Test
	void testGetPointResponse_NegativePointAmount() {
		Long pointAmount = -1000L;

		GetPointResponse response = GetPointResponse.builder()
			.pointAmount(pointAmount)
			.build();

		assertNotNull(response);
		assertEquals(pointAmount, response.pointAmount());
	}
}

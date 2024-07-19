package store.novabook.front.api.coupon.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CreateCouponResponseTest {

	@Test
	void testCreateCouponResponse_Valid() {
		Long expectedId = 123L;
		CreateCouponResponse response = new CreateCouponResponse(expectedId);

		assertNotNull(response);
		assertEquals(expectedId, response.id());
	}

	@Test
	void testCreateCouponResponse_NullId() {
		CreateCouponResponse response = new CreateCouponResponse(null);

		assertNotNull(response);
		assertNull(response.id());
	}
}

package store.novabook.front.api.coupon.dto.response;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import store.novabook.front.api.coupon.domain.CouponStatus;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;

class GetUsedCouponHistoryResponseTest {

	@Test
	void testGetUsedCouponHistoryResponse_Valid() {
		LocalDateTime usedAt = LocalDateTime.now();
		String name = "Sample Coupon";
		CouponType type = CouponType.GENERAL;
		CouponStatus status = CouponStatus.USED;
		long discountAmount = 500;
		DiscountType discountType = DiscountType.AMOUNT;

		GetUsedCouponHistoryResponse response = GetUsedCouponHistoryResponse.builder()
			.usedAt(usedAt)
			.name(name)
			.type(type)
			.status(status)
			.discountAmount(discountAmount)
			.discountType(discountType)
			.build();

		assertNotNull(response);
		assertEquals(usedAt, response.usedAt());
		assertEquals(name, response.name());
		assertEquals(type, response.type());
		assertEquals(status, response.status());
		assertEquals(discountAmount, response.discountAmount());
		assertEquals(discountType, response.discountType());
	}

	@Test
	void testGetUsedCouponHistoryResponse_NullFields() {
		GetUsedCouponHistoryResponse response = GetUsedCouponHistoryResponse.builder()
			.usedAt(null)
			.name(null)
			.type(null)
			.status(null)
			.discountAmount(0)
			.discountType(null)
			.build();

		assertNotNull(response);
		assertNull(response.usedAt());
		assertNull(response.name());
		assertNull(response.type());
		assertNull(response.status());
		assertEquals(0, response.discountAmount());
		assertNull(response.discountType());
	}
}

package store.novabook.front.store.order.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RequestPayCancelMessageTest {

	@Test
	void testBuilder() {
		String expectedOrderCode = "ORDER123";
		Long expectedCouponId = 1L;
		Long expectedUsePointAmount = 1000L;
		Long expectedMemberId = 2L;
		Long expectedEarnPointAmount = 500L;
		String expectedPaymentKey = "PAYKEY";
		String expectedStatus = "SUCCESS";

		RequestPayCancelMessage message = RequestPayCancelMessage.builder()
			.orderCode(expectedOrderCode)
			.couponId(expectedCouponId)
			.usePointAmount(expectedUsePointAmount)
			.memberId(expectedMemberId)
			.earnPointAmount(expectedEarnPointAmount)
			.paymentKey(expectedPaymentKey)
			.status(expectedStatus)
			.build();

		assertEquals(expectedOrderCode, message.getOrderCode());
		assertEquals(expectedCouponId, message.getCouponId());
		assertEquals(expectedUsePointAmount, message.getUsePointAmount());
		assertEquals(expectedMemberId, message.getMemberId());
		assertEquals(expectedEarnPointAmount, message.getEarnPointAmount());
		assertEquals(expectedPaymentKey, message.getPaymentKey());
		assertEquals(expectedStatus, message.getStatus());
	}

	@Test
	void testGettersAndSetters() {
		RequestPayCancelMessage message = RequestPayCancelMessage.builder().build();

		message.setOrderCode("ORDER123");
		message.setCouponId(1L);
		message.setUsePointAmount(1000L);
		message.setMemberId(2L);
		message.setEarnPointAmount(500L);
		message.setPaymentKey("PAYKEY");
		message.setStatus("SUCCESS");

		assertEquals("ORDER123", message.getOrderCode());
		assertEquals(1L, message.getCouponId());
		assertEquals(1000L, message.getUsePointAmount());
		assertEquals(2L, message.getMemberId());
		assertEquals(500L, message.getEarnPointAmount());
		assertEquals("PAYKEY", message.getPaymentKey());
		assertEquals("SUCCESS", message.getStatus());
	}
}

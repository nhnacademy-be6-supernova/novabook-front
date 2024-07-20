package store.novabook.front.api.member.member.dto.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateMemberCouponResponseTest {

	@Test
	void testCreateMemberCouponResponse_Builder() {
		Long expectedCouponId = 123L;

		CreateMemberCouponResponse response = CreateMemberCouponResponse.builder()
			.couponId(expectedCouponId)
			.build();

		assertThat(response).isNotNull();
		assertThat(response.couponId()).isEqualTo(expectedCouponId);
	}

	@Test
	void testCreateMemberCouponResponse_NullCouponId() {
		Long expectedCouponId = null;

		CreateMemberCouponResponse response = CreateMemberCouponResponse.builder()
			.couponId(expectedCouponId)
			.build();

		assertThat(response).isNotNull();
		assertThat(response.couponId()).isNull();
	}
}

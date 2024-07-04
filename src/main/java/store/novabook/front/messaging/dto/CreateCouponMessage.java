package store.novabook.front.messaging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import store.novabook.front.api.coupon.domain.CouponType;

@Builder
public record CreateCouponMessage(@NotNull Long memberId, @NotNull CouponType couponType,
								  @NotNull Long couponTemplateId) {
	public static CreateCouponMessage fromEntity(Long memberId, CouponType couponType, Long couponTemplateId) {
		return CreateCouponMessage.builder()
			.memberId(memberId)
			.couponType(couponType)
			.couponTemplateId(couponTemplateId)
			.build();
	}
}

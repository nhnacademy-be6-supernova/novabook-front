package store.novabook.front.api.member.coupon.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import store.novabook.front.api.coupon.domain.CouponType;

@Builder
public record DownloadCouponMessageRequest(@NotNull String uuid, @NotNull CouponType couponType,
										   Long couponTemplateId) {
}

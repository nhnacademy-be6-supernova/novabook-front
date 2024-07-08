package store.novabook.front.messaging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import store.novabook.front.api.coupon.domain.CouponType;

@Builder
public record DownloadCouponMessageRequest(@NotNull String uuid, @NotNull CouponType couponType,
										   @NotNull Long couponTemplateId) {
}

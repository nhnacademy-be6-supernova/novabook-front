package store.novabook.front.messaging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import store.novabook.front.api.coupon.domain.CouponType;

@Builder
public record DownloadCouponMessageRequest(@NotNull Long uuid, @NotNull CouponType couponType, @NotNull Long couponTemplateId) {
}

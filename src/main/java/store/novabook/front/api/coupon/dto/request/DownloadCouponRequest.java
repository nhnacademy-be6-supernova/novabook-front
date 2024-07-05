package store.novabook.front.api.coupon.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DownloadCouponRequest(@NotNull Long couponTemplateId) {
}

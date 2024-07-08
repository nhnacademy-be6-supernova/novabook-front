package store.novabook.front.messaging.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import store.novabook.front.api.coupon.domain.CouponType;

@Builder
public record CreateCouponNotifyMessage(@NotNull Long uuid, @NotNull List<Long> memberIdList,
										@NotNull CouponType couponType, Long couponTemplateId) {
}

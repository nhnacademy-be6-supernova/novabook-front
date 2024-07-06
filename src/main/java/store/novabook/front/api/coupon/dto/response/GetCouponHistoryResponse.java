package store.novabook.front.api.coupon.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import store.novabook.front.api.coupon.domain.CouponStatus;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;

@Builder
public record GetCouponHistoryResponse(LocalDateTime createdAt, String name, CouponType type, CouponStatus status,
									   long discountAmount, DiscountType discountType) {
}

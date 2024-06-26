package store.novabook.front.api.coupon.dto.request;

import java.time.LocalDateTime;

import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;

public record CreateCouponTemplateRequest(String name, CouponType type, long discountAmount, DiscountType discountType, long maxDiscountAmount,
										  long minPurchaseAmount, LocalDateTime startedAt, LocalDateTime expirationAt, Integer usePeriod) {
}
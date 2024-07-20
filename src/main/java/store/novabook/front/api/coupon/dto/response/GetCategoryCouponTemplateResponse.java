package store.novabook.front.api.coupon.dto.response;

import java.time.LocalDateTime;

import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;

public record GetCategoryCouponTemplateResponse(Long categoryId, Long id, CouponType type, String name,
												long discountAmount, DiscountType discountType, long maxDiscountAmount,
												long minPurchaseAmount, LocalDateTime startedAt,
												LocalDateTime expirationAt, int usePeriod) {
}

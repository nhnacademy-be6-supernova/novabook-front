package store.novabook.front.api.coupon.dto.request;

import java.time.LocalDateTime;

import store.novabook.front.api.coupon.domain.DiscountType;

public record CreateBookCouponRequest(Long bookId, String name, long discountAmount, DiscountType discountType,
									  long maxDiscountAmount, long minPurchaseAmount, LocalDateTime startedAt,
									  LocalDateTime expirationAt) {
}
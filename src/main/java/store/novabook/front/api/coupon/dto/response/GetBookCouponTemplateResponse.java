package store.novabook.front.api.coupon.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;

@Builder
public record GetBookCouponTemplateResponse(
	Long bookId, Long id, CouponType type, String name, long discountAmount,
	DiscountType discountType, long maxDiscountAmount, long minPurchaseAmount,
	LocalDateTime startedAt, LocalDateTime expirationAt, int usePeriod
) {
}

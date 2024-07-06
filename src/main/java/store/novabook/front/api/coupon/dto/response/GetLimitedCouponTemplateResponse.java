package store.novabook.front.api.coupon.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;

/**
 * {@code GetLimitedCouponTemplateResponse} 레코드는 카테고리 쿠폰 템플릿 조회 응답을 나타냅니다.
 *
 * @param quantity          카테고리 ID
 * @param id                쿠폰 템플릿 ID
 * @param type              쿠폰 타입
 * @param name              쿠폰 이름
 * @param discountAmount    할인 금액
 * @param discountType      할인 유형
 * @param maxDiscountAmount 최대 할인 금액
 * @param minPurchaseAmount 최소 구매 금액
 * @param startedAt         쿠폰 정책 시작 날짜
 * @param expirationAt      쿠폰 정책 만료 날짜
 * @param usePeriod         사용 가능 기간
 */
@Builder
public record GetLimitedCouponTemplateResponse(Long quantity, Long id, CouponType type, String name,
											   long discountAmount, DiscountType discountType, long maxDiscountAmount,
											   long minPurchaseAmount, LocalDateTime startedAt,
											   LocalDateTime expirationAt, int usePeriod) {
}

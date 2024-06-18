package com.nhnacademy.novabook_front.api.coupon.dto.request;

import java.time.LocalDateTime;

import com.nhnacademy.novabook_front.api.coupon.domain.DiscountType;

public record CreateCouponRequest(String name, long discountAmount, DiscountType discountType, long maxDiscountAmount,
								  long minPurchaseAmount, LocalDateTime startedAt, LocalDateTime expirationAt) {
}
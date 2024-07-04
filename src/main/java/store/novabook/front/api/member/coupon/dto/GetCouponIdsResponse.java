package store.novabook.front.api.member.coupon.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record GetCouponIdsResponse(List<Long> couponIds) {
}

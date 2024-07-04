package store.novabook.front.api.coupon.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record GetCouponHistoryAllResponse(List<GetCouponHistoryResponse> couponList) {
}

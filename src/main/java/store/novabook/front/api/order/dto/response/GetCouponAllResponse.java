package store.novabook.front.api.order.dto.response;

import java.util.List;

import lombok.Builder;

/**
 * {@code GetCouponAllResponse} 레코드는 모든 쿠폰 조회 응답을 나타냅니다.
 *
 * @param couponResponseList 쿠폰 응답 리스트
 */
@Builder
public record GetCouponAllResponse(List<GetCouponResponse> couponResponseList) {
}

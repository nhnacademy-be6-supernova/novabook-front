package store.novabook.front.api.coupon.dto.response;

import java.util.List;

import lombok.Builder;

/**
 * {@code GetCategoryCouponTemplateAllResponse} 레코드는 모든 카테고리 쿠폰 템플릿 조회 응답을 나타냅니다.
 *
 * @param responseList 카테고리 쿠폰 템플릿 응답 리스트
 */
@Builder
public record GetCategoryCouponTemplateAllResponse(List<GetCategoryCouponTemplateResponse> responseList) {
}

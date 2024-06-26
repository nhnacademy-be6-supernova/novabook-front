package store.novabook.front.api.coupon.service;

import store.novabook.front.common.response.PageResponse;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponTemplateResponse;

public interface CouponService {
	PageResponse<GetBookCouponTemplateResponse> getBookCouponTemplateAll(int page, int size);

	PageResponse<GetCategoryCouponTemplateResponse> getCategoryCouponTemplateAll(int page, int size);

	PageResponse<GetCouponTemplateResponse> getCouponTemplateAll(CouponType type, int page, int size);
}

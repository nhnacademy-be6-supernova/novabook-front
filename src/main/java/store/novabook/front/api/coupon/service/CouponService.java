package store.novabook.front.api.coupon.service;

import store.novabook.front.api.PageResponse;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.dto.request.CreateBookCouponTemPlateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCategoryCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponTemplateResponse;

public interface CouponService {
	PageResponse<GetBookCouponTemplateResponse> getBookCouponTemplateAll(int page, int size);

	PageResponse<GetCategoryCouponTemplateResponse> getCategoryCouponTemplateAll(int page, int size);

	PageResponse<GetCouponTemplateResponse> getCouponTemplateAll(CouponType type, int page, int size);

	void createGeneralTemplateCoupon(CreateCouponTemplateRequest request);

	void createBookTemplateCoupon (CreateBookCouponTemPlateRequest request);

	void createCategoryTemplateCoupon(CreateCategoryCouponTemplateRequest request);

}

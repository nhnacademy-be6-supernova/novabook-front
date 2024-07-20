package store.novabook.front.api.coupon.service;

import java.util.List;

import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.dto.request.CreateBookCouponTemPlateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCategoryCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateLimitedCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateAllResponse;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetLimitedCouponTemplateResponse;
import store.novabook.front.common.response.PageResponse;

public interface CouponService {
	PageResponse<GetBookCouponTemplateResponse> getBookCouponTemplateAll(int page, int size);

	PageResponse<GetCategoryCouponTemplateResponse> getCategoryCouponTemplateAll(Boolean isValid, int page, int size);

	PageResponse<GetCouponTemplateResponse> getCouponTemplateAll(CouponType type, Boolean isValid, int page,
		int size, String sort);

	PageResponse<GetLimitedCouponTemplateResponse> getLimitedCouponTemplateAll(boolean isValid, int page,
		int size);

	void createGeneralTemplateCoupon(CreateCouponTemplateRequest request);

	void createBookTemplateCoupon(CreateBookCouponTemPlateRequest request);

	void createCategoryTemplateCoupon(CreateCategoryCouponTemplateRequest request);

	void createLimitedTemplateCoupon(CreateLimitedCouponTemplateRequest request);

	GetBookCouponTemplateAllResponse getBookCouponTemplate(Long bookId, Boolean isValid);

	GetCategoryCouponTemplateAllResponse getCategoryCouponTemplate(List<Long> categoryIdList, Boolean isValid);
}

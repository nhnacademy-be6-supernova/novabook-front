package store.novabook.front.api.coupon.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.PageResponse;
import store.novabook.front.api.coupon.CouponClient;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.dto.request.CreateBookCouponTemPlateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCategoryCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponTemplateResponse;
import store.novabook.front.api.coupon.service.CouponService;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
	private final CouponClient couponClient;

	@Override
	public PageResponse<GetBookCouponTemplateResponse> getBookCouponTemplateAll(int page, int size) {
		return couponClient.getBookCouponTemplateAll(page, size);
	}

	@Override
	public PageResponse<GetCategoryCouponTemplateResponse> getCategoryCouponTemplateAll(int page, int size) {
		return couponClient.getCategoryCouponTemplateAll(page, size);
	}

	@Override
	public PageResponse<GetCouponTemplateResponse> getCouponTemplateAll(CouponType type, int page, int size) {
		return couponClient.getCouponTemplateAll(type, page, size);
	}

	@Override
	public void createGeneralTemplateCoupon(CreateCouponTemplateRequest request) {
		couponClient.createCouponTemplate(request);
	}

	@Override
	public void createBookTemplateCoupon(CreateBookCouponTemPlateRequest request) {
		couponClient.createBookCouponTemplate(request);
	}

	@Override
	public void createCategoryTemplateCoupon(CreateCategoryCouponTemplateRequest request) {
		couponClient.createCategoryCouponTemplate(request);
	}
}

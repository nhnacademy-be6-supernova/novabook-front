package store.novabook.front.api.coupon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.coupon.client.CouponClient;
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
import store.novabook.front.api.coupon.service.CouponService;
import store.novabook.front.common.response.PageResponse;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
	private final CouponClient couponClient;

	@Override
	public PageResponse<GetBookCouponTemplateResponse> getBookCouponTemplateAll(int page, int size) {
		return couponClient.getBookCouponTemplateAll(page, size);
	}

	@Override
	public PageResponse<GetCategoryCouponTemplateResponse> getCategoryCouponTemplateAll(Boolean isValid, int page,
		int size) {
		return couponClient.getCategoryCouponTemplateAll(isValid, page, size);
	}

	@Override
	public PageResponse<GetCouponTemplateResponse> getCouponTemplateAll(CouponType type, Boolean isValid, int page,
		int size, String sort) {
		return couponClient.getCouponTemplateAll(type, isValid, page, size, sort);
	}

	@Override
	public PageResponse<GetLimitedCouponTemplateResponse> getLimitedCouponTemplateAll(boolean isValid, int page,
		int size) {
		return couponClient.getLimitedCouponTemplateAll(isValid, page, size);
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

	@Override
	public void createLimitedTemplateCoupon(CreateLimitedCouponTemplateRequest request) {
		couponClient.createLimitedCouponTemplate(request);
	}

	@Override
	public GetBookCouponTemplateAllResponse getBookCouponTemplate(Long bookId, Boolean isValid) {
		return couponClient.getCouponTemplateByBookId(bookId, isValid).getBody();
	}

	@Override
	public GetCategoryCouponTemplateAllResponse getCategoryCouponTemplate(List<Long> categoryIdList, Boolean isValid) {
		return couponClient.getCategoryCouponTemplateAllByCategoryIdAll(categoryIdList, isValid);
	}

}

package store.novabook.front.api.coupon.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.dto.request.CreateBookCouponTemPlateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCategoryCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.response.CreateCouponResponse;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponTemplateResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

@FeignClient(name = "couponClient", url = "http://localhost:9777/api/v1/coupon/templates")
public interface CouponClient {

	//일반 쿠폰 템플릿 전체 불러오기 쿠폰 타입도 알려줘야 함
	@GetMapping
	PageResponse<GetCouponTemplateResponse> getCouponTemplateAll(@RequestParam(required = false) CouponType type,
		@RequestParam int page, @RequestParam int size);

	//책 쿠폰 템플릿 전체  불러오기
	@GetMapping("/book")
	PageResponse<GetBookCouponTemplateResponse> getBookCouponTemplateAll(@RequestParam int page,
		@RequestParam int size);

	//카테고리 템플릿 쿠폰 전체 불러오기
	@GetMapping("/category")
	PageResponse<GetCategoryCouponTemplateResponse> getCategoryCouponTemplateAll(@RequestParam int page,
		@RequestParam int size);

	//일반 쿠폰 템플릿 생성 요청
	@PostMapping
	ApiResponse<CreateCouponResponse> createCouponTemplate(@RequestBody CreateCouponTemplateRequest request);

	//책 쿠폰 템플릿 생성 요청
	@PostMapping("/book")
	ApiResponse<CreateCouponResponse> createBookCouponTemplate(@RequestBody CreateBookCouponTemPlateRequest request);

	//카테고리 쿠폰 템플릿 생성 요청
	@PostMapping("/category")
	ApiResponse<CreateCouponResponse> createCategoryCouponTemplate(
		@RequestBody CreateCategoryCouponTemplateRequest request);
}
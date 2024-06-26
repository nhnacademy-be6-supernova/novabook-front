package store.novabook.front.api.coupon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.ApiResponse;
import store.novabook.front.api.PageResponse;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.dto.request.CreateBookCouponTemPlateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCategoryCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.CreateCouponTemplateRequest;
import store.novabook.front.api.coupon.dto.request.UpdateCouponExpirationRequest;
import store.novabook.front.api.coupon.dto.response.CreateCouponResponse;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponTemplateResponse;

@FeignClient(name = "couponClient", url = "http://localhost:8070/api/v1")
public interface CouponClient {

	//일반 쿠폰 템플릿 전체 불러오기 쿠폰 타입도 알려줘야 함
	@GetMapping("/coupon-templates")
	PageResponse<GetCouponTemplateResponse> getCouponTemplateAll(@RequestParam(required = false) CouponType type,@RequestParam int page, @RequestParam int size);

	//책 쿠폰 템플릿 전체  불러오기
	@GetMapping("/book-coupon-templates")
	PageResponse<GetBookCouponTemplateResponse> getBookCouponTemplateAll(@RequestParam int page, @RequestParam int size);

	//카테고리 템플릿 쿠폰 전체 불러오기
	@GetMapping("/category-coupon-templates")
	PageResponse<GetCategoryCouponTemplateResponse> getCategoryCouponTemplateAll(@RequestParam int page, @RequestParam int size);

	//일반 쿠폰 템플릿 생성 요청
	@PostMapping("/coupon-templates")
	ApiResponse<CreateCouponResponse> createCouponTemplate(@RequestBody CreateCouponTemplateRequest request);

	//책 쿠폰 템플릿 생성 요청
	@PostMapping("/book-coupon-templates")
	ApiResponse<CreateCouponResponse> createBookCouponTemplate(@RequestBody CreateBookCouponTemPlateRequest request);

	//카테고리 쿠폰 템플릿 생성 요청
	@PostMapping("/category-coupon-templates")
	ApiResponse<CreateCouponResponse> createCategoryCouponTemplate(@RequestBody CreateCategoryCouponTemplateRequest request);

	@PostMapping("/coupons/category")
	ResponseEntity<CreateCouponResponse> saveCategoryCoupon(@RequestBody CreateCategoryCouponTemplateRequest request);

	@PutMapping("/coupons/expiration")
	ResponseEntity<Void> updateCouponExpiration(@RequestBody UpdateCouponExpirationRequest request);
}
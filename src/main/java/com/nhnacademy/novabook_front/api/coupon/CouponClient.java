package com.nhnacademy.novabook_front.api.coupon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.novabook_front.api.coupon.dto.request.CreateBookCouponRequest;
import com.nhnacademy.novabook_front.api.coupon.dto.request.CreateCategoryCouponRequest;
import com.nhnacademy.novabook_front.api.coupon.dto.request.CreateCouponRequest;
import com.nhnacademy.novabook_front.api.coupon.dto.request.UpdateCouponExpirationRequest;
import com.nhnacademy.novabook_front.api.coupon.dto.response.CreateCouponResponse;

@FeignClient(name = "couponClient", url = "http://localhost:8087")
public interface CouponClient {

	@PostMapping("/coupons")
	ResponseEntity<CreateCouponResponse> saveGeneralCoupon(@RequestBody CreateCouponRequest request);

	@PostMapping("/coupons/book")
	ResponseEntity<CreateCouponResponse> saveBookCoupon(@RequestBody CreateBookCouponRequest request);

	@PostMapping("/coupons/category")
	ResponseEntity<CreateCouponResponse> saveCategoryCoupon(@RequestBody CreateCategoryCouponRequest request);

	@PutMapping("/coupons/expiration")
	ResponseEntity<Void> updateCouponExpiration(@RequestBody UpdateCouponExpirationRequest request);
}
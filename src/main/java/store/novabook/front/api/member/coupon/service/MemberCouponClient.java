package store.novabook.front.api.member.coupon.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import store.novabook.front.api.member.coupon.dto.GetCouponIdsResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "memberCouponClient")
public interface MemberCouponClient {
	@GetMapping
	ApiResponse<GetCouponIdsResponse> getMemberCoupon();
}

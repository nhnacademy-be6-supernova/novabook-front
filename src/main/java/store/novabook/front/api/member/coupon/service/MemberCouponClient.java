package store.novabook.front.api.member.coupon.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponHistoryAllResponse;
import store.novabook.front.api.coupon.dto.response.GetUsedCouponHistoryAllResponse;
import store.novabook.front.api.member.coupon.dto.GetCouponIdsResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "memberCouponClient")
public interface MemberCouponClient {
	@GetMapping
	ApiResponse<GetCouponIdsResponse> getMemberCoupon();

	@GetMapping("/is-valid")
	ApiResponse<GetCouponAllResponse> getMemberCouponValidByMemberId();

	@GetMapping("/history")
	ApiResponse<GetCouponHistoryAllResponse> getMemberCouponHistoryByMemberId(@RequestParam int page, @RequestParam int size);

	@GetMapping("/history/used")
	ApiResponse<GetUsedCouponHistoryAllResponse> getMemberUsedCouponHistoryByMemberId(@RequestParam int page, @RequestParam int size);

}

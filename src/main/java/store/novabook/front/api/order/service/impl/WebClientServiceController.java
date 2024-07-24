package store.novabook.front.api.order.service.impl;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import store.novabook.front.api.coupon.dto.request.GetCouponAllRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressListResponse;
import store.novabook.front.api.member.coupon.dto.GetCouponIdsResponse;
import store.novabook.front.api.order.WebClientService;
import store.novabook.front.api.order.dto.response.GetWrappingPaperAllResponse;
import store.novabook.front.api.point.dto.GetMemberPointResponse;
import store.novabook.front.common.response.ApiResponse;

@RestController
@RequestMapping("/api/webclient")
@RequiredArgsConstructor
public class WebClientServiceController {

	private final WebClientService webClientService;

	@GetMapping("/categories")
	public Mono<Set<Long>> fetchCategoryIdsAsync(@RequestParam Set<Long> bookIds) {
		return webClientService.fetchCategoryIdsAsync(bookIds);
	}

	@GetMapping("/wrapping-papers")
	public Mono<ApiResponse<GetWrappingPaperAllResponse>> getWrappingPaperAllList() {
		return webClientService.getWrappingPaperAllList();
	}

	@GetMapping("/delivery-fee/recent")
	public Mono<ApiResponse<GetDeliveryFeeResponse>> getRecentDeliveryFee() {
		return webClientService.getRecentDeliveryFee();
	}

	@GetMapping("/coupons")
	public Mono<ApiResponse<GetCouponIdsResponse>> fetchCouponIdsAsync() {
		return webClientService.fetchCouponIdsAsync();
	}

	@GetMapping("/member/point")
	public Mono<ApiResponse<GetMemberPointResponse>> getMemberPointTotal() {
		return webClientService.getMemberPointTotal();
	}

	@GetMapping("/addresses")
	public Mono<ApiResponse<GetMemberAddressListResponse>> getMemberAddressAll() {
		return webClientService.getMemberAddressAll();
	}

	@PostMapping("/coupons/sufficient")
	public Mono<ApiResponse<GetCouponAllResponse>> getSufficientCouponAll(
		@RequestBody GetCouponAllRequest couponRequest) {
		return webClientService.getSufficientCouponAll(couponRequest);
	}
}

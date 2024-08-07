package store.novabook.front.store.member;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.api.coupon.dto.request.DownloadCouponRequest;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateAllResponse;
import store.novabook.front.api.coupon.service.CouponService;
import store.novabook.front.api.member.coupon.dto.DownloadCouponMessageRequest;
import store.novabook.front.api.member.member.service.MemberCouponService;
import store.novabook.front.common.handler.HandleWithAlert;

@Slf4j
@HandleWithAlert
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class MemberCouponController {

	private final MemberCouponService memberCouponService;
	private final CouponService couponService;

	// 선착순 쿠폰 다운로드
	@PostMapping("/download/limited")
	public ResponseEntity<Void> downloadLimited(@RequestBody @Valid DownloadCouponMessageRequest request) {
		memberCouponService.downloadLimitedCoupon(request);
		return ResponseEntity.ok().build();
	}

	// 쿠폰 다운로드 (일반, 도서, 카테고리)
	@PostMapping("/download")
	public ResponseEntity<Void> downloadCoupon(@Valid @RequestBody DownloadCouponRequest request) {
		memberCouponService.downloadCoupon(request);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/book/{bookId}")
	public ResponseEntity<GetBookCouponTemplateAllResponse> getCouponAllForBook(@PathVariable Long bookId,
		Model model) {
		return ResponseEntity.ok(couponService.getBookCouponTemplate(bookId, true));

	}

	@GetMapping("/categories")
	public ResponseEntity<GetCategoryCouponTemplateAllResponse> getCouponAllForCategory(
		@RequestParam List<Long> categoryIdList, Model model) {
		return ResponseEntity.ok(couponService.getCategoryCouponTemplate(categoryIdList, true));
	}

}

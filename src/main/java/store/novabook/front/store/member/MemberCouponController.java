package store.novabook.front.store.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import store.novabook.front.api.coupon.dto.request.CreateMemberCouponRequest;
import store.novabook.front.api.coupon.dto.request.DownloadCouponRequest;
import store.novabook.front.api.coupon.dto.response.GetBookCouponTemplateAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCategoryCouponTemplateAllResponse;
import store.novabook.front.api.coupon.service.CouponService;
import store.novabook.front.api.member.member.service.MemberCouponService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class MemberCouponController {

	private final MemberCouponService memberCouponService;
	private final CouponService couponService;

	// 컨트롤러 메소드
	@PostMapping("/download/limited")
	public ResponseEntity<Void> downloadLimited(@Valid @RequestBody CreateMemberCouponRequest request) {
		memberCouponService.downloadLimitedCoupon(request);
		return ResponseEntity.ok().build();
	}

	// 컨트롤러 메소드
	@PostMapping("/download")
	public ResponseEntity<Map<String, String>> downloadCoupon(@Valid @RequestBody DownloadCouponRequest request) {
		String message = memberCouponService.downloadCoupon(request);
		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		return ResponseEntity.ok(response);
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

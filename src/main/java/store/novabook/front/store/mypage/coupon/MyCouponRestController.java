package store.novabook.front.store.mypage.coupon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.coupon.dto.response.GetCouponHistoryResponse;
import store.novabook.front.api.coupon.dto.response.GetUsedCouponHistoryResponse;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.member.member.service.MemberCouponService;

@RestController
@RequestMapping("/mypage/coupons")
@RequiredArgsConstructor
public class MyCouponRestController {

	private final MemberGradeService memberGradeService;
	private final MemberCouponService memberCouponService;

	@GetMapping("/api/issued")

	public Page<GetCouponHistoryResponse> getIssuedCouponsApi(@PageableDefault(size = 5) Pageable pageable) {
		return memberCouponService.getMyCouponHistoryAll(pageable);
	}

	@GetMapping("/api/used")

	public Page<GetUsedCouponHistoryResponse> getUsedCouponsApi(@PageableDefault(size = 5) Pageable pageable) {
		return memberCouponService.getMyUsedCouponHistory(pageable);
	}
}

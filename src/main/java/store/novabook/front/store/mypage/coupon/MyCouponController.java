package store.novabook.front.store.mypage.coupon;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.member.member.service.MemberCouponService;

@Controller
@RequestMapping("/mypage/coupons")
@RequiredArgsConstructor
public class MyCouponController {

	private final MemberGradeService memberGradeService;
	private final MemberCouponService memberCouponService;

	@GetMapping
	public String getMyCoupon(@Qualifier("createdPageable") @PageableDefault(size = 5) Pageable createdPageable,
		@Qualifier("usedPageable") @PageableDefault(size = 5) Pageable usedPageable, Model model) {
		GetCouponAllResponse myCouponList = memberCouponService.getMyCouponAllWithValid();
		model.addAttribute("myCouponList", myCouponList);
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/coupon/coupon_history";
	}
}

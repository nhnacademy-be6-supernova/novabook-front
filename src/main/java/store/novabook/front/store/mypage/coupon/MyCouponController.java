package store.novabook.front.store.mypage.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;

@Controller
@RequestMapping("/mypage/coupons")
@RequiredArgsConstructor
public class MyCouponController {
	private final MemberGradeService memberGradeService;

	@GetMapping
	public String getMyCoupon(Model model) {
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/coupon/coupon_history";
	}
}

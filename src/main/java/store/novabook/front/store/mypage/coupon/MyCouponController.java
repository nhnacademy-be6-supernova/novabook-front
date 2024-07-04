package store.novabook.front.store.mypage.coupon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponHistoryAllResponse;
import store.novabook.front.api.coupon.dto.response.GetUsedCouponHistoryAllResponse;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.member.member.service.MemberCouponService;

@Controller
@RequestMapping("/mypage/coupons")
@RequiredArgsConstructor
public class MyCouponController {

	private static final String DEFAULT_PAGE_SIZE = "5";
	public static final String DEFAULT_PAGE = "0";

	private final MemberGradeService memberGradeService;
	private final MemberCouponService memberCouponService;

	@GetMapping
	public String getMyCoupon(@RequestParam(defaultValue = DEFAULT_PAGE) int page,
		@RequestParam(defaultValue = DEFAULT_PAGE) int usagePage,
		@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size, Model model) {
		GetCouponAllResponse myCouponList = memberCouponService.getMyCouponAllWithValid();
		GetCouponHistoryAllResponse createdList =  memberCouponService.getMyCouponHistoryAll(page, size);
		GetUsedCouponHistoryAllResponse usedList = memberCouponService.getMyUsedCouponHistory(usagePage, size);

		model.addAttribute("myCouponList", myCouponList);
		model.addAttribute("createdList", createdList);
		model.addAttribute("usedList", usedList);
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/coupon/coupon_history";
	}
}

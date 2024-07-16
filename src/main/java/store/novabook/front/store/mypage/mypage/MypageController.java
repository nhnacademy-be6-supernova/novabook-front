package store.novabook.front.store.mypage.mypage;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.member.member.service.MemberCouponService;
import store.novabook.front.api.order.service.OrdersBookService;
import store.novabook.front.api.point.service.PointService;

@RequestMapping("/mypage")
@Controller
@RequiredArgsConstructor
public class MypageController {
	private final MemberGradeService memberGradeService;
	private final OrdersBookService ordersBookService;
	private final PointService pointService;
	private final MemberCouponService memberCouponService;
	private static final int PAGE = 0;
	private static final int SIZE = 2;

	@GetMapping
	public String getMypage(Model model) {
		model.addAttribute("pointHistories", pointService.getPointHistories(PAGE, SIZE));
		model.addAttribute("orders", ordersBookService.getOrdersBookAll(PAGE, SIZE));
		model.addAttribute("coupons", memberCouponService.getMyCouponHistoryAll(Pageable.ofSize(SIZE)));
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/mypage_index";
	}
}
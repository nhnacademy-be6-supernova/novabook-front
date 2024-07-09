package store.novabook.front.store.mypage.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;

@RequestMapping("/mypage/orders")
@Controller
@RequiredArgsConstructor
public class MypageOrderController {

	private final MemberGradeService memberGradeService;

	@GetMapping
	public String getOrderAll(Model model) {
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/order/order_list";
	}

	@GetMapping("/detail")
	public String getOrderDetail(Model model) {
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/order/order_list_detail";
	}

	@GetMapping("/cancel")
	public String getOrderCancelAll(Model model) {
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/order/order_cancel_list";
	}

}

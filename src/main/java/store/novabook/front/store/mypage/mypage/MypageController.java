package store.novabook.front.store.mypage.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.order.service.OrdersBookService;

@RequestMapping("/mypage")
@Controller
@RequiredArgsConstructor
public class MypageController {
	private final MemberGradeService memberGradeService;
	private final OrdersBookService ordersBookService;

	@GetMapping
	public String getMypage(Model model) {
		model.addAttribute("orders", ordersBookService.getAllOrdersBook(0, 2));
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/mypage_index";
	}
}
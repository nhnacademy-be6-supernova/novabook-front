package store.novabook.front.store.mypage.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.order.service.OrdersBookService;

@RequestMapping("/mypage/orders")
@Controller
@RequiredArgsConstructor
public class MypageOrderController {

	private final MemberGradeService memberGradeService;
	private final OrdersBookService ordersBookService;
	private static final String DEFAULT_PAGE_SIZE = "10";
	private static final String DEFAULT_PAGE_PAGE = "0";

	@GetMapping
	public String getOrderAll(Model model,@RequestParam(defaultValue = DEFAULT_PAGE_PAGE) int page,
		@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size) {
		model.addAttribute("orders", ordersBookService.getAllOrdersBook(page, size));
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/order/order_list";
	}

	@GetMapping("/detail/{ordersId}")
	public String getOrderDetail(@PathVariable Long ordersId, Model model) {
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		model.addAttribute("ordersDetail", ordersBookService.getOrderDetail(ordersId));
		return "store/mypage/order/order_list_detail";
	}

	@GetMapping("/cancel")
	public String getOrderCancelAll(Model model) {
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/order/order_cancel_list";
	}

}

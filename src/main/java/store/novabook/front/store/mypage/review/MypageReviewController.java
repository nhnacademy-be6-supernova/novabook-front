package store.novabook.front.store.mypage.review;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.service.ReviewService;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.order.dto.response.GetOrdersBookReviewIdResponse;
import store.novabook.front.api.order.service.OrdersBookService;
import store.novabook.front.common.response.PageResponse;

@RequestMapping("/mypage/reviews")
@Controller
@RequiredArgsConstructor
public class MypageReviewController {
	private final MemberGradeService memberGradeService;
	private final OrdersBookService ordersBookService;
	private static final String DEFAULT_PAGE_SIZE = "10";
	private static final String DEFAULT_PAGE_PAGE = "0";

	@GetMapping
	public String getReviewAll(Model model, @RequestParam(defaultValue = DEFAULT_PAGE_PAGE) int page,
		@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size) {
		PageResponse<GetOrdersBookReviewIdResponse> ordersBook = ordersBookService.getOrdersBookReviewId(page, size);
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		model.addAttribute("ordersBook", ordersBook);

		return "store/mypage/review/review_list";
	}
}

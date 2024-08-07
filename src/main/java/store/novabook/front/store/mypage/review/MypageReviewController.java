package store.novabook.front.store.mypage.review;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.dto.request.UpdateReviewRequest;
import store.novabook.front.api.book.dto.response.GetReviewResponse;
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
	private final ReviewService reviewService;
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

	@GetMapping("/{ordersBookId}")
	public String writeReview(Model model, @PathVariable Long ordersBookId) {
		model.addAttribute("ordersBookId", ordersBookId);
		return "store/mypage/review/review_write";
	}

	@GetMapping("/{reviewId}/update")
	public String getUpdateReview(Model model, @PathVariable Long reviewId) {
		GetReviewResponse review = reviewService.getReviewById(reviewId);
		model.addAttribute("review", review);

		return "store/mypage/review/review_modify";
	}

	@PostMapping("/{ordersBookId}")
	public String createReview(@PathVariable("ordersBookId") Long ordersBookId,
		@RequestParam("content") String content,
		@RequestParam("score") int score,
		@RequestParam("reviewImages") List<MultipartFile> reviewImages) {
		reviewService.createReview(content, score, reviewImages, ordersBookId);
		return "redirect:/mypage/reviews";
	}

	@PostMapping("/{reviewId}/update")
	public String postUpdateReview(@PathVariable Long reviewId,
		@RequestParam("content") String content,
		@RequestParam("score") int score
	) {
		UpdateReviewRequest request = new UpdateReviewRequest(reviewId, content, score);
		reviewService.updateReview(request, reviewId);

		return "redirect:/mypage/reviews";
	}
}

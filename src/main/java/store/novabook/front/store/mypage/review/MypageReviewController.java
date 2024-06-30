package store.novabook.front.store.mypage.review;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;

@RequestMapping("/mypage/reviews")
@Controller
@RequiredArgsConstructor
public class MypageReviewController {
	private final MemberGradeService memberGradeService;

	@GetMapping
	public String getReviewAll(Model model) {
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/review/review_list";
	}
}

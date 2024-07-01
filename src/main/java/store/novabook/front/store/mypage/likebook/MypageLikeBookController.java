package store.novabook.front.store.mypage.likebook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.service.LikeService;
import store.novabook.front.api.member.grade.service.MemberGradeService;

@RequiredArgsConstructor
@RequestMapping("/mypage/likebooks")
@Controller
public class MypageLikeBookController {

	private final LikeService likeService;
	private final MemberGradeService memberGradeService;
	private static final String PAGE_SIZE = "10";
	private static final String PAGE = "0";

	@GetMapping
	public String getLikeBookAll(Model model,
		@RequestParam(defaultValue = PAGE) int page,
		@RequestParam(defaultValue = PAGE_SIZE) int size) {
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		model.addAttribute("LikeBooks", likeService.getBookLikeAllPage(page, size));

		return "store/mypage/likebook/like_book_list";
	}

	@GetMapping("/{likesId}")
	public String deleteLikeBook(@PathVariable Long likesId) {
		likeService.deleteBookLike(likesId);
		return "redirect:/mypage/likebooks";
	}
}

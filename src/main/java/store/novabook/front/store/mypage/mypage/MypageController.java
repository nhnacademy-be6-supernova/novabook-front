package store.novabook.front.store.mypage.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;

@RequestMapping("/mypage")
@Controller
@RequiredArgsConstructor
public class MypageController {
	private final MemberGradeService memberGradeService;
	private static final Long MEMBER_ID = 7L;
	@GetMapping
	public String getMypage(Model model) {
		model.addAttribute("grade", memberGradeService.getMemberGrade(MEMBER_ID));
		return "store/mypage/mypage_index";
	}
}
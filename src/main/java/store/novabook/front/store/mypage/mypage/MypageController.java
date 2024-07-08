package store.novabook.front.store.mypage.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;

@RequestMapping("/mypage")
@Controller
@RequiredArgsConstructor
public class MypageController {
	private final MemberGradeService memberGradeService;
	@PutMapping
	public String getMypage(Model model) {
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/mypage_index";
	}
}
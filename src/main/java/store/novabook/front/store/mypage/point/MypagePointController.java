package store.novabook.front.store.mypage.point;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;

@RequestMapping("/mypage/points")
@Controller
@RequiredArgsConstructor
public class MypagePointController {
	private final MemberGradeService memberGradeService;

	@GetMapping
	public String getPointAll(Model model) {

		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/point/point_list";
	}
}

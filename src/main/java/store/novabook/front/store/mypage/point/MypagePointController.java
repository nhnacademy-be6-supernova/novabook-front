package store.novabook.front.store.mypage.point;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.point.service.PointService;

@RequestMapping("/mypage/points")
@Controller
@RequiredArgsConstructor
public class MypagePointController {
	private static final String DEFAULT_PAGE_SIZE = "10";
	public static final String DEFAULT_PAGE = "0";
	private final MemberGradeService memberGradeService;
	private final PointService pointService;

	@GetMapping
	public String getPointAll(Model model, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
		@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size) {
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		model.addAttribute("point", pointService.getMemberPoint().pointAmount());
		model.addAttribute("pointHistories", pointService.getPointHistories(page, size));
		return "store/mypage/point/point_list";
	}
}

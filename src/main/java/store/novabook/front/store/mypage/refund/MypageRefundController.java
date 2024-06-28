package store.novabook.front.store.mypage.refund;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;

@RequestMapping("/mypage/refunds")
@Controller
@RequiredArgsConstructor
public class MypageRefundController {
	private final MemberGradeService memberGradeService;
	private static final Long MEMBER_ID = 7L;

	@GetMapping
	public String getRefundAll(Model model) {
		model.addAttribute("grade", memberGradeService.getMemberGrade(MEMBER_ID));
		return "store/mypage/refund/refund_list";
	}

}

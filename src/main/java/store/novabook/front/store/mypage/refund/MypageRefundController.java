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
	@GetMapping
	public String getRefundAll(Model model) {
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		return "store/mypage/refund/refund_list";
	}

}

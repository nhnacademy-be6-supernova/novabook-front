package store.novabook.front.store.mypage.information;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.member.member.service.MemberService;

@Controller
@RequestMapping("/mypage/information")
@RequiredArgsConstructor
public class MyInformationController {
	private static final Long MEMBER_ID = 7L;

	private final MemberService memberService;
	private final MemberGradeService memberGradeService;

	@GetMapping
	public String getMyInformation(Model model) {
		memberService.getMemberById(MEMBER_ID);
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		model.addAttribute("member", memberService.getMemberById(MEMBER_ID));
		return "store/mypage/information/my_information";
	}





}

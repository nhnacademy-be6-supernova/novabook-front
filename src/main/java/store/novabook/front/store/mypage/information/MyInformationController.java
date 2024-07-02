package store.novabook.front.store.mypage.information;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.grade.service.MemberGradeService;
import store.novabook.front.api.member.member.dto.request.UpdateMemberPasswordRequest;
import store.novabook.front.api.member.member.dto.request.UpdateMemberRequest;
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

	@PostMapping("/update")
	public String updateMember(@Valid UpdateMemberRequest updateMemberRequest) {
		memberService.updateMember(MEMBER_ID, updateMemberRequest);
		return "redirect:/mypage/information";
	}

	@PostMapping("/password")
	public String updateMemberPassword(@Valid UpdateMemberPasswordRequest updateMemberPasswordRequest) {
		memberService.updateMemberPassword(MEMBER_ID, updateMemberPasswordRequest);
		return "redirect:/mypage/information";
	}

}

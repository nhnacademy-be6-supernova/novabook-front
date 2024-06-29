package store.novabook.front.store.mypage.information;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.UpdateMemberNameRequest;
import store.novabook.front.api.member.member.dto.UpdateMemberNumberRequest;
import store.novabook.front.api.member.member.dto.UpdateMemberPasswordRequest;
import store.novabook.front.api.member.member.service.MemberService;

@Controller
@RequestMapping("/mypage/information")
@RequiredArgsConstructor
public class MyInformationController {
	private static final Long MEMBER_ID = 7L;

	private final MemberService memberService;

	@GetMapping
	public String getMyInformation(Model model) {
		memberService.getMemberById(MEMBER_ID);
		model.addAttribute("member", memberService.getMemberById(MEMBER_ID));
		return "store/mypage/information/my_information";
	}

	@PostMapping("/name")
	public String updateMemberName(UpdateMemberNameRequest updateMemberNameRequest) {
		memberService.updateMemberName(MEMBER_ID, updateMemberNameRequest);
		return "redirect:/mypage/information";
	}

	@PostMapping("/number")
	public String updateMemberNumber(UpdateMemberNumberRequest updateMemberNumberRequest) {
		memberService.updateMemberNumber(MEMBER_ID, updateMemberNumberRequest);
		return "redirect:/mypage/information";
	}

	@PostMapping("/password")
	public String updateMemberPassword(UpdateMemberPasswordRequest updateMemberPasswordRequest) {
		memberService.updateMemberPassword(MEMBER_ID, updateMemberPasswordRequest);
		return "redirect:/mypage/information";
	}

}

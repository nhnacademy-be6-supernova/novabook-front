package store.novabook.front.store.mypage.withdraw;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.request.DeleteMemberRequest;
import store.novabook.front.api.member.member.service.MemberService;

@Controller
@RequestMapping("/mypage/withdraw")
@RequiredArgsConstructor
public class MemberWithdrawController {
	private final MemberService memberService;
	private static final Long MEMBER_ID = 9L;

	@GetMapping
	public String memberWithdraw(Model model) {
		memberService.getMemberById(MEMBER_ID);
		model.addAttribute("member", memberService.getMemberById(MEMBER_ID));
		return "store/mypage/withdraw/withdraw";
	}

	@PostMapping("/withdraw")
	public String memberUpdateToWithdraw(DeleteMemberRequest deleteMemberRequest) {
		memberService.deleteMember(MEMBER_ID, deleteMemberRequest);
		return "redirect:/";
	}
}

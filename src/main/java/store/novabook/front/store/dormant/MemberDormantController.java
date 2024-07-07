package store.novabook.front.store.dormant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.service.MemberService;

@Controller
@RequestMapping("/dormant")
@RequiredArgsConstructor
public class MemberDormantController {

	private final MemberService memberService;

	@GetMapping
	public String memberWithdraw(Model model) {
		memberService.getMemberById();
		model.addAttribute("member", memberService.getMemberById());
		return "store/dormant/dormant";
	}
}

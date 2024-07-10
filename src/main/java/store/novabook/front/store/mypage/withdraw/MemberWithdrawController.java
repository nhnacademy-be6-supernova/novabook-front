package store.novabook.front.store.mypage.withdraw;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.request.DeleteMemberRequest;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.util.CookieUtil;

@Controller
@RequestMapping("/mypage/withdraw")
@RequiredArgsConstructor
public class MemberWithdrawController {
	private final MemberService memberService;

	@GetMapping
	public String memberWithdraw(Model model) {
		memberService.getMemberById();
		model.addAttribute("member", memberService.getMemberById());
		return "store/mypage/withdraw/withdraw";
	}

	@PostMapping("/withdraw")
	public String memberUpdateToWithdraw(DeleteMemberRequest deleteMemberRequest, HttpServletRequest request, HttpServletResponse response) {
		memberService.deleteMember(deleteMemberRequest);
		memberService.logout(response);
		return "redirect:/";
	}


}

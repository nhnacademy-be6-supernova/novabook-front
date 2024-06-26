package store.novabook.front.api.member.member.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.LoginMemberResponse;
import store.novabook.front.api.member.member.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/user/form")
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	public String register(@ModelAttribute CreateMemberRequest createMemberRequest) {
		memberService.createMember(createMemberRequest);
		return "redirect:/login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute LoginMemberRequest loginMemberRequest, Model model,
		HttpServletResponse response) {
		LoginMemberResponse loginMemberResponse = memberService.getMember(loginMemberRequest);
		// response.addHeader("Authorization", "Bearer " + tokenDto.token());
		Cookie cookie = new Cookie("Authorization", loginMemberResponse.token());
		cookie.setMaxAge(60 * 60 * 24 * 7);
		cookie.setPath("/");
		response.addCookie(cookie);

		if (loginMemberResponse.token().isEmpty()) {
			// model.addAttribute("loginMemberResponse", loginMemberResponse);
			return "redirect:/";
		} else {
			return "redirect:/login";
		}
	}

}

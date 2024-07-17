package store.novabook.front.api.member.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.request.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.request.LoginMembersRequest;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.exception.ForbiddenException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/user/form")
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	public String register(@ModelAttribute @Valid CreateMemberRequest createMemberRequest, HttpServletRequest request) {
		memberService.createMember(createMemberRequest);
		return "redirect:/login";

	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute LoginMembersRequest loginMembersRequest, HttpServletResponse response,
		RedirectAttributes redirectAttributes) {
		try {
			return memberService.login(loginMembersRequest, response);
		} catch (ForbiddenException e) {
			redirectAttributes.addFlashAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다. ");
			return "redirect:/login";
		}
	}

	@PostMapping("/payco/link/login")
	public String paycoLinkLogin(@ModelAttribute LoginMembersRequest loginMembersRequest,
		HttpServletResponse response) {
		memberService.login(loginMembersRequest, response);
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		memberService.logout(response);
		return "redirect:/";
	}
}

package store.novabook.front.api.member.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.request.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.request.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.response.LoginMemberResponse;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.security.exception.AlreadyLoginException;
import store.novabook.front.common.security.exception.NotLoginException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/user/form")
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	public String register(@ModelAttribute @Valid CreateMemberRequest createMemberRequest,
		HttpServletRequest request) {
		memberService.createMember(createMemberRequest);
		return "redirect:/login";

	}

	@PostMapping("/login")
	public String login(@ModelAttribute LoginMemberRequest loginMemberRequest,
		Model model,
		HttpServletRequest request,
		HttpServletResponse response) {

		LoginMemberResponse loginMemberResponse = memberService.getMember(loginMemberRequest, response);
		String authorization = response.getHeader("Authorization");
		String refresh = response.getHeader("Refresh");

		if (authorization != null && !authorization.isEmpty()) {
			String accessToken = authorization.replace("Bearer ", "");
			String refreshToken = refresh.replace("Bearer ", "");

			Cookie accessCookie = new Cookie("Authorization", accessToken);
			accessCookie.setMaxAge(60 * 60 * 24 * 7);
			accessCookie.setPath("/");
			response.addCookie(accessCookie);

			Cookie refreshCookie = new Cookie("Refresh", refreshToken);
			refreshCookie.setMaxAge(60 * 60 * 24 * 7);
			refreshCookie.setPath("/");
			response.addCookie(refreshCookie);

			if (loginMemberResponse.token().isEmpty()) {
				return "redirect:/login";
			} else {
				return "redirect:/";
			}
		}
		return authorization;
	}

	@PostMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		memberService.logout();
		deleteCookie(request, response, "Authorization");
		deleteCookie(request, response, "Refresh");
		return "redirect:/";
	}

	public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					cookie.setValue("");
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}
}

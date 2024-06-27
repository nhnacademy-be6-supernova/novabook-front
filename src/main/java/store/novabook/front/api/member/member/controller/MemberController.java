package store.novabook.front.api.member.member.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.CreateMemberRequest;
import store.novabook.front.api.member.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.LoginMemberResponse;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.api.member.dto.CreateMemberRequest;
import store.novabook.front.api.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.dto.LoginMemberResponse;
import store.novabook.front.api.member.service.MemberService;
import store.novabook.front.common.config.TokenHolder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/user/form")
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	public String register(@ModelAttribute @Valid CreateMemberRequest createMemberRequest) {
		memberService.createMember(createMemberRequest);
		return "redirect:/login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute LoginMemberRequest loginMemberRequest, Model model, HttpServletResponse response) {
		LoginMemberResponse loginMemberResponse = memberService.getMember(loginMemberRequest);
		String token = loginMemberResponse.token();

		if (token != null && !token.isEmpty()) {
			// 로그인 성공 시 토큰을 TokenHolder에 설정
			TokenHolder.setToken(token);

			// 필요 시 쿠키에도 추가
			Cookie cookie = new Cookie("Authorization", token);
			cookie.setMaxAge(60 * 60 * 24 * 7);
			cookie.setPath("/");
			response.addCookie(cookie);
		Cookie cookie = new Cookie("Authorization", loginMemberResponse.token());
		cookie.setMaxAge(60 * 60 * 24 * 7);
		cookie.setPath("/");
		response.addCookie(cookie);

		if (loginMemberResponse.token().isEmpty()) {
			return "redirect:/";
			return "redirect:/dashboard"; // 로그인 성공 후 리다이렉트할 경로
		} else {
			return "redirect:/login"; // 로그인 실패 시 리다이렉트할 경로
		}
	}

	// @PostMapping("/login")
	// public String login(@ModelAttribute LoginMemberRequest loginMemberRequest, Model model,
	// 	HttpServletResponse response) {
	// 	LoginMemberResponse loginMemberResponse = memberService.getMember(loginMemberRequest);
	// 	String token = loginMemberResponse.token();
	//
	// 	if (token != null && !token.isEmpty()) {
	// 		// 로그인 성공 시 토큰을 TokenHolder에 설정
	// 		TokenHolder.setToken(token);
	//
	// 		// 필요 시 쿠키에도 추가
	// 		Cookie cookie = new Cookie("Authorization", token);
	// 		cookie.setMaxAge(60 * 60 * 24 * 7);
	// 		cookie.setPath("/");
	// 		response.addCookie(cookie);
	//
	// 		return "redirect:/dashboard"; // 로그인 성공 후 리다이렉트할 경로
	// 	} else {
	// 		return "redirect:/login"; // 로그인 실패 시 리다이렉트할 경로
	// 	}
	// }

}

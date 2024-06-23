package com.nhnacademy.novabook_front.api.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.novabook_front.api.member.dto.LoginMemberRequest;
import com.nhnacademy.novabook_front.api.member.dto.LoginMemberResponse;
import com.nhnacademy.novabook_front.api.member.service.MemberService;
import com.nhnacademy.novabook_front.api.member.dto.CreateMemberRequest;

import lombok.RequiredArgsConstructor;

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
	public String login(@ModelAttribute LoginMemberRequest loginMemberRequest, Model model) {
		LoginMemberResponse loginMemberResponse = memberService.login(loginMemberRequest);
		if(loginMemberResponse.success()){
			// model.addAttribute("loginMemberResponse", loginMemberResponse);
			return "redirect:/";
		}
		else {
			return "redirect:/login";
		}
	}

}

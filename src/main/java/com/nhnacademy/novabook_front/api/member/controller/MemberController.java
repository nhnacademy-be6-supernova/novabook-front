package com.nhnacademy.novabook_front.api.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.novabook_front.api.member.dto.CreateMemberRequest;
import com.nhnacademy.novabook_front.api.member.service.MemberServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/user/form")
public class MemberController {

	private final MemberServiceImpl memberServiceImpl;

	@PostMapping
	public String register(@ModelAttribute CreateMemberRequest createMemberRequest) {
		memberServiceImpl.createMember(createMemberRequest);
		return "redirect:/login";
	}

}

package com.nhnacademy.novabook_front.api.memberAddress.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.novabook_front.api.memberAddress.dto.CreateMemberAddressRequest;
import com.nhnacademy.novabook_front.api.memberAddress.service.MemberAddressService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/addresses")
public class MemberAddressController {

	private final MemberAddressService memberAddressService;

	@PostMapping
	public String register(@ModelAttribute CreateMemberAddressRequest createMemberAddressRequest) {
		memberAddressService.createMemberAddress(createMemberAddressRequest);
		return "redirect:/mypage/addresses";
	}


}

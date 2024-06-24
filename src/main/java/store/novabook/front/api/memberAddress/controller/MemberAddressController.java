package store.novabook.front.api.memberAddress.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.memberAddress.dto.CreateMemberAddressRequest;
import store.novabook.front.api.memberAddress.service.MemberAddressService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/addresses")
public class MemberAddressController {

	private final MemberAddressService memberAddressService;

	@PostMapping
	public String register(@ModelAttribute CreateMemberAddressRequest createMemberAddressRequest) {
		// TODO : 로그인 되면 header로 보낼 예정
		Long memberId = 6L;
		memberAddressService.createMemberAddress(createMemberAddressRequest, memberId);
		return "redirect:/mypage/addresses";
	}




}

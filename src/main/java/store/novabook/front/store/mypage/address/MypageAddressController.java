package store.novabook.front.store.mypage.address;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.address.dto.CreateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.GetMemberAddressResponse;
import store.novabook.front.api.member.address.dto.UpdateMemberAddressRequest;
import store.novabook.front.api.member.address.service.MemberAddressService;
import store.novabook.front.api.member.grade.service.MemberGradeService;

@RequestMapping("/mypage/addresses")
@Controller
@RequiredArgsConstructor
public class MypageAddressController {
	private final MemberAddressService memberAddressService;
	private final MemberGradeService memberGradeService;
	private static final Long MEMBER_ID = 7L;

	@GetMapping
	public String getAddressForm(Model model) {
		List<GetMemberAddressResponse> responses = memberAddressService.getMemberAddresses(MEMBER_ID);
		model.addAttribute("grade", memberGradeService.getMemberGrade(MEMBER_ID));
		model.addAttribute("addressList", responses);
		return "store/mypage/address/address_list";
	}

	@PostMapping("/address/{addressId}/update")
	public String updateAddress(@PathVariable Long addressId, @Valid UpdateMemberAddressRequest request) {
		memberAddressService.updateMemberAddress(addressId, request);
		return "redirect:/mypage/addresses";
	}

	@PostMapping
	public String register(@Valid @ModelAttribute CreateMemberAddressRequest createMemberAddressRequest) {
		memberAddressService.createMemberAddress(createMemberAddressRequest, MEMBER_ID);
		return "redirect:/mypage/addresses";
	}

	@GetMapping("/address/{addressId}/delete")
	public String deleteAddress(@PathVariable Long addressId) {
		memberAddressService.deleteMemberAddress(addressId);
		return "redirect:/mypage/addresses";
	}
}

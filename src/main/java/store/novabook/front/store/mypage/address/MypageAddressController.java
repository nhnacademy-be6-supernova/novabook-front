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
import store.novabook.front.api.member.address.dto.request.CreateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.request.UpdateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressResponse;
import store.novabook.front.api.member.address.service.MemberAddressService;
import store.novabook.front.api.member.grade.service.MemberGradeService;

@RequestMapping("/mypage/addresses")
@Controller
@RequiredArgsConstructor
public class MypageAddressController {
	public static final String REDIRECT_MYPAGE_ADDRESSES = "redirect:/mypage/addresses";
	private final MemberAddressService memberAddressService;
	private final MemberGradeService memberGradeService;

	@GetMapping
	public String getAddress(Model model) {
		List<GetMemberAddressResponse> responses = memberAddressService.getMemberAddressAll();
		model.addAttribute("grade", memberGradeService.getMemberGrade());
		model.addAttribute("addressList", responses);
		return "store/mypage/address/address_list";
	}

	@PostMapping("/address/{addressId}/update")
	public String updateAddress(@PathVariable Long addressId, @Valid UpdateMemberAddressRequest request) {
		memberAddressService.updateMemberAddress(addressId, request);
		return REDIRECT_MYPAGE_ADDRESSES;
	}

	@PostMapping
	public String register(@Valid @ModelAttribute CreateMemberAddressRequest createMemberAddressRequest) {
		memberAddressService.createMemberAddress(createMemberAddressRequest);
		return REDIRECT_MYPAGE_ADDRESSES;
	}

	@GetMapping("/address/{addressId}/delete")
	public String deleteAddress(@PathVariable Long addressId) {
		memberAddressService.deleteMemberAddress(addressId);
		return REDIRECT_MYPAGE_ADDRESSES;
	}
}

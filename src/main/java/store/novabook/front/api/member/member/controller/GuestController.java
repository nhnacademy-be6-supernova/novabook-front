package store.novabook.front.api.member.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.service.GuestService;
import store.novabook.front.common.exception.NotFoundException;
import store.novabook.front.store.order.dto.GetGuestOrderHistoryRequest;
import store.novabook.front.store.order.dto.GetOrderDetailResponse;

@Controller
@RequestMapping("/guest")
@RequiredArgsConstructor
public class GuestController {

	private final GuestService guestService;

	@PostMapping("/login")
	public String getOrder(@ModelAttribute GetGuestOrderHistoryRequest request, Model model,
		RedirectAttributes redirectAttributes) {
		try {
			GetOrderDetailResponse response = guestService.login(request);
			model.addAttribute("ordersDetail", response);
			return "store/mypage/order/order_list_detail";
		} catch (NotFoundException e) {
			redirectAttributes.addFlashAttribute("guestError", e.getMessage());
			return "redirect:/login#guest-order-tab";
		}
	}
}

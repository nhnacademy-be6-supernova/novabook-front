package store.novabook.front.api.member.member.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.service.GuestService;
import store.novabook.front.store.order.dto.GetGuestOrderHistoryRequest;
import store.novabook.front.store.order.dto.GetOrderDetailResponse;

@Controller
@RequestMapping("/guest")
@RequiredArgsConstructor
public class GuestController {

	private static final Logger log = LoggerFactory.getLogger(GuestController.class);
	private final GuestService guestService;

	@PostMapping("/login")
	public String getOrder(@ModelAttribute GetGuestOrderHistoryRequest request, Model model) {
		GetOrderDetailResponse response = guestService.login(request);
		log.info(response.toString());
		model.addAttribute("ordersDetail",response);
		return "store/mypage/order/order_list_detail";
	}
}

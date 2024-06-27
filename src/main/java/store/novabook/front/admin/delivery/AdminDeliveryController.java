package store.novabook.front.admin.delivery;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.delivery.dto.CreateDeliveryFeeRequest;
import store.novabook.front.api.delivery.dto.GetDeliveryFeeResponse;
import store.novabook.front.api.delivery.service.DeliveryFeeService;
import store.novabook.front.common.response.PageResponse;

@RequestMapping("/admin/deliveries/delivery")
@Controller
@RequiredArgsConstructor
public class AdminDeliveryController {
	private final DeliveryFeeService deliveryFeeService;
	private static final String PAGE_SIZE = "5";

	@GetMapping("/form")
	public String getDeliveryForm(@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = PAGE_SIZE) int size, Model model) {

		PageResponse<GetDeliveryFeeResponse> deliveries = deliveryFeeService.getDeliveryFeeAllPage(page, size);
		model.addAttribute("deliveries", deliveries);

		return "admin/delivery/delivery_fee_form";
	}

	@PostMapping
	public String createDelivery(@ModelAttribute CreateDeliveryFeeRequest request) {
		deliveryFeeService.createDeliveryFee(request);
		return "redirect:/admin/deliveries/delivery/form";
	}
}

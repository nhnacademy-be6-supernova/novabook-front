package store.novabook.front.store.order;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import store.novabook.front.api.order.client.WrappingPaperClient;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;

@RequestMapping("/orders")
@Controller
public class OrderController {

	private final WrappingPaperClient wrappingPaperClient;

	public OrderController(WrappingPaperClient wrappingPaperClient) {
		this.wrappingPaperClient = wrappingPaperClient;
	}

	@GetMapping("/order/form")
	public String getOrderForm(Model model) {
		// List<GetWrappingPaperResponse> papers = wrappingPaperClient.getWrappingPaperAllList().getBody().papers();
		// model.addAttribute("papers", papers);
		return "store/order/order_form";
	}

	@GetMapping("/order/{orderId}/success")
	public String getOrderSuccessPage(@PathVariable Long orderId) {

		// 넘어 올 정보 입력
		// 상품 정보, 맴버 정보

		return "store/order/order_success";
	}

	@GetMapping("/order/{orderId}/fail")
	public String getOrderFailPage(@PathVariable Long orderId) {
		return "store/order/order_fail";
	}

}

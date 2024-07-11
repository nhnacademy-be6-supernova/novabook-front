package store.novabook.front.admin.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.order.service.OrdersBookService;

@RequestMapping("/admin/orders")
@Controller
public class AdminOrderProcessController {

	@GetMapping
	public String getOrderAll(Model model) {
		return "admin/order/order_process_list";
	}

}

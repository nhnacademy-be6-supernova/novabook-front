package store.novabook.front.admin.order;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.order.service.OrderService;
import store.novabook.front.api.order.service.OrdersBookService;
import store.novabook.front.store.order.dto.UpdateOrdersAdminRequest;

@RequestMapping("/admin/orders")
@Controller
@RequiredArgsConstructor
public class AdminOrderProcessController {

	private final OrderService orderService;
	private static final String DEFAULT_PAGE_SIZE = "10";
	private static final String DEFAULT_PAGE_PAGE = "0";

	@GetMapping
	public String getOrderAll(Model model, @RequestParam(defaultValue = DEFAULT_PAGE_PAGE) int page,
		@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size) {
		model.addAttribute("orders", orderService.getOrderAllAdmin(page, size));
		return "admin/order/order_process_list";
	}

	@PostMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @ModelAttribute UpdateOrdersAdminRequest request) {
		orderService.update(id, request);
		return ResponseEntity.noContent().build();
	}

}

package store.novabook.front.store.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.order.service.OrderService;

@RequiredArgsConstructor
@RequestMapping("/orders/order")
@Controller
public class OrdersRabbitMqController {

	private final OrderService orderService;

	@PostMapping("/{orderId}/cancel")
	public void requestPayCancel(@PathVariable Long orderId) {
		orderService.sendRequestPayCancel(orderId);
	}
}

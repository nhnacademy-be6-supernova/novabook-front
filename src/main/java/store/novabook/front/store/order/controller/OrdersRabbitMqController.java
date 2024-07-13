package store.novabook.front.store.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.order.service.OrderService;


@RequiredArgsConstructor
@RequestMapping("/orders/")
@RestController
public class OrdersRabbitMqController {

	private final OrderService orderService;

	@PostMapping("/{orderId}/cancel")
	public ResponseEntity<Object> requestPayCancel(@PathVariable Long orderId) {
		orderService.sendRequestPayCancel(orderId);
		return ResponseEntity.ok().build();
	}

}

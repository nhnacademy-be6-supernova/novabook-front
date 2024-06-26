package store.novabook.front.store.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import store.novabook.front.store.order.dto.OrderTemporaryForm;
import store.novabook.front.store.order.service.RedisOrderServive;

@RequestMapping("/orders")
@RestController
public class OrderRestController {

	private final RedisOrderServive orderService;

	public OrderRestController(RedisOrderServive orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/order/form")
	public ResponseEntity<Void> getOrderForm(@RequestBody OrderTemporaryForm orderTemporaryForm) {
		orderService.create(orderTemporaryForm);
		return ResponseEntity.ok().build();
	}
}

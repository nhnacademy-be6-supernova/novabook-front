package store.novabook.front.store.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import store.novabook.front.store.order.dto.OrderTemporaryForm;
import store.novabook.front.store.order.service.RedisOrderService;
import store.novabook.front.store.order.service.impl.RedisOrderServiceImpl;

@RequestMapping("/orders")
@RestController
public class OrderRestController {

	private final RedisOrderService orderService;

	public OrderRestController(RedisOrderServiceImpl orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/order/form")
	public ResponseEntity<HttpStatus> getOrderForm(@Valid @RequestBody OrderTemporaryForm orderTemporaryForm) {
		orderService.create(orderTemporaryForm);
		return ResponseEntity.ok(HttpStatus.CREATED);
	}
}

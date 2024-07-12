package store.novabook.front.store.order.controller;

import static store.novabook.front.common.util.CookieUtil.*;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import store.novabook.front.store.order.dto.OrderTemporaryFormRequest;
import store.novabook.front.store.order.service.RedisOrderService;
import store.novabook.front.store.order.service.impl.RedisOrderServiceImpl;

@RequestMapping("/orders")
@RestController
public class OrderRestController {

	private final RedisOrderService orderService;

	public OrderRestController(RedisOrderServiceImpl orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/order/form/submit")
	public ResponseEntity<UUID> createOrderForm(@Valid @RequestBody OrderTemporaryFormRequest orderTemporaryFormRequest,
		@CookieValue(name = GUEST_COOKIE_NAME, required = false) Cookie guestCookie
		) {
		String guestCookieValue = (guestCookie != null) ? guestCookie.getValue() : null;
		UUID orderUUID = orderService.createOrderForm(orderTemporaryFormRequest, guestCookieValue);
		return ResponseEntity.status(HttpStatus.CREATED).body(orderUUID);
	}
}

package com.nhnacademy.novabook_front.store.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/orders")
@Controller
public class OrderController {

	// WrapperClient client;

	@GetMapping("/order/form")
	public String getOrderForm() {

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

package store.novabook.front.store.order.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import store.novabook.front.api.order.dto.request.TossPaymentRequest;
import store.novabook.front.api.order.service.OrderService;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.book.dto.BookListDTO;

@RequestMapping("/orders")
@Controller
public class OrderController {
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/order/form")
	public String getOrderForm(Model model) {
		BookDTO 임시데이터 = BookDTO.builder()
			.id(5L)
			.price(1000)
			.name("불량감자 (불량 감자의 바삭한 여행)")
			.discount(1000)
			.isPackage(true)
			.quantity(10)
			.imageUrl("https://shopping-phinf.pstatic.net/main_3247334/32473349454.20230927071208.jpg")
			.build();
		BookListDTO build = BookListDTO.builder().bookDTOS(List.of(임시데이터)).build();

		model.addAttribute("items", build.bookDTOS());
		model.addAttribute("orderDTO", orderService.getOrder(build.bookDTOS(), 7L));
		return "store/order/order_form";
	}

	@GetMapping("/order/{orderId}/success")
	public String getOrderSuccessPage(@PathVariable Long orderId,
		@Valid @ModelAttribute TossPaymentRequest tossPaymentRequest) {
		// 가주문이 이미 만들어진 유저만 생성 가능
		if (Boolean.TRUE.equals(orderService.existOrderUUID(UUID.fromString(tossPaymentRequest.orderId())))) {
			orderService.createOrder(tossPaymentRequest);
		} else {
			throw new IllegalArgumentException("부적절한 접근입니다.");
		}
		return "store/order/order_success";
	}

	@GetMapping("/order/{orderId}/fail")
	public String getOrderFailPage(@PathVariable Long orderId) {
		return "store/order/order_fail";
	}

}

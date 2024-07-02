package store.novabook.front.store.order.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
		// 임시 데이터
		BookDTO 임시데이터 = BookDTO.builder()
			.id(5L)
			.price(1000)
			.name("불량감자 (불량 감자의 바삭한 여행)")
			.discount(1000)
			.isPackage(true)
			.quantity(10)
			.build();
		BookListDTO build = BookListDTO.builder().bookDTOS(List.of(임시데이터)).build();
		model.addAttribute("items", build.bookDTOS());
		model.addAttribute("orderDTO", orderService.getOrder(build.bookDTOS(), 7L));
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

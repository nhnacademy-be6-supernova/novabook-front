package store.novabook.front.store.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import store.novabook.front.api.order.service.OrderService;
import store.novabook.front.store.book.dto.BookListDTO;

@RequestMapping("/orders")
@Controller
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/order/form")
	public String getOrderForm( Model model, @ModelAttribute BookListDTO bookListDTO) {
		model.addAttribute("items", bookListDTO.bookDTOS());
		orderService.getOrder(bookListDTO.bookDTOS(), 1L);
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

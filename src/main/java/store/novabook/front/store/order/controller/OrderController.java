package store.novabook.front.store.order.controller;

import static store.novabook.front.common.util.CookieUtil.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.order.dto.PaymentType;
import store.novabook.front.api.order.dto.request.PaymentRequest;
import store.novabook.front.api.order.dto.request.TossPaymentRequest;
import store.novabook.front.api.order.service.OrderService;
import store.novabook.front.common.security.aop.CurrentMembers;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.book.dto.BookListDTO;
import store.novabook.front.store.order.repository.RedisOrderNonMemberRepository;
import store.novabook.front.store.order.repository.RedisOrderRepository;

@RequestMapping("/orders")
@RequiredArgsConstructor
@Controller
public class OrderController {
	private final OrderService orderService;
	@PostMapping("/order/form")
	public String getOrderForm(@CurrentMembers(required = false) Long memberId, Model model, @RequestBody BookListDTO bookListDTO) {
		model.addAttribute("memberId", memberId);
		model.addAttribute("items", bookListDTO.bookDTOS());
		model.addAttribute("orderDTO", orderService.getOrder(bookListDTO.bookDTOS(), memberId));
		return "store/order/order_form";
	}


	/**
	 * 실제 트랜잭션을 시작하기 위한 로직
	 * @param
	 * @param tossPaymentRequest
	 * @param orderMemberId
	 * @param orderUUID
	 * @return
	 */
	@GetMapping("/order/toss/success")
	public String getTossOrderSuccessPage(
		@CurrentMembers(required = false) Long memberId,
		@Valid @ModelAttribute TossPaymentRequest tossPaymentRequest,
		@RequestParam(value = "memberId", required = false) Long orderMemberId,
		@RequestParam("orderId") UUID orderUUID,
		Model model) {

		if (orderService.isInvalidAccess(memberId, orderUUID, orderMemberId)) {
			throw new IllegalArgumentException("부적절한 접근입니다.");
		}

		// 전달받은 orderUUID, orderMemberId는 가주문서와 검증용으로 사용
		orderService.createOrder(new PaymentRequest(PaymentType.TOSS, orderMemberId, orderUUID, tossPaymentRequest));
		model.addAttribute("memberDto",orderService.getSuccessView(orderUUID, orderMemberId));

		return "store/order/order_success";
	}

	/**
	 * 토스 결제 실패 시 fail url 리다이렉션
	 * @return
	 */
	@GetMapping("/order/toss/fail")
	public String getOrderFailPage() {
		return "store/order/order_fail";
	}

}

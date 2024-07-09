package store.novabook.front.store.order.controller;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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


	@GetMapping("/order/form")
	public String getOrderForm(@CurrentMembers Long memberId, Model model) {
		model.addAttribute("memberId", memberId);
		model.addAttribute("items", build.bookDTOS());
		model.addAttribute("orderDTO", orderService.getOrder(build.bookDTOS(), 7L));
		return "store/order/order_form";
	}


	/**
	 * 실제 트랜잭션을 시작하기 위한 로직
	 * @param memberId
	 * @param tossPaymentRequest
	 * @param orderMemberId
	 * @param orderUUID
	 * @return
	 */
	@GetMapping("/order/toss/success")
	public String getTossOrderSuccessPage(@CurrentMembers Long memberId,
		@Valid @ModelAttribute TossPaymentRequest tossPaymentRequest,
		@ModelAttribute("memberId") Long orderMemberId,
		@ModelAttribute("orderUUID") UUID orderUUID) {

		if (orderService.isInvalidAccess(memberId, orderUUID, orderMemberId)) {
			throw new IllegalArgumentException("부적절한 접근입니다.");
		}

		// 전달받은 orderUUID, orderMemberId는 가주문서와 검증용으로 사용
		orderService.createOrder(new PaymentRequest(PaymentType.TOSS, orderMemberId, orderUUID, tossPaymentRequest));
		return "store/order/order_success";
	}

	@GetMapping("/order/toss/fail")
	public String getOrderFailPage() {
		return "store/order/order_fail";
	}
}

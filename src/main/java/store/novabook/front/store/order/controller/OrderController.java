package store.novabook.front.store.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import store.novabook.front.api.order.dto.PaymentType;
import store.novabook.front.api.order.dto.request.PaymentRequest;
import store.novabook.front.api.order.dto.request.TossPaymentRequest;
import store.novabook.front.api.order.service.OrderService;
import store.novabook.front.api.order.service.ReactiveOrderService;
import store.novabook.front.common.security.aop.CurrentMembers;
import store.novabook.front.store.book.dto.BookListDTO;
import store.novabook.front.store.order.dto.OrderViewDTO;

@Slf4j
@RequestMapping("/orders")
@RequiredArgsConstructor
@Controller
public class OrderController {

	private final OrderService orderService;
	private final ReactiveOrderService reactiveOrderService;

	@PostMapping("/order/form")
	public DeferredResult<String> getOrderForm(@CurrentMembers(required = false) Long memberId,
		@RequestParam("order") String orderJson, Model model) {
		ObjectMapper objectMapper = new ObjectMapper();
		BookListDTO bookListDTO;

		try {
			bookListDTO = objectMapper.readValue(orderJson, BookListDTO.class);
		} catch (JsonProcessingException e) {
			log.error("", e);
			DeferredResult<String> errorResult = new DeferredResult<>();
			errorResult.setResult("error/500");
			return errorResult;
		}

		DeferredResult<String> deferredResult = new DeferredResult<>();
		Mono<OrderViewDTO> orderViewDTOMono = reactiveOrderService.getOrder(bookListDTO.bookDTOS(), memberId);

		orderViewDTOMono.subscribe(orderViewDTO -> {
			model.addAttribute("memberId", memberId);
			model.addAttribute("items", bookListDTO.bookDTOS());
			model.addAttribute("orderDTO", orderViewDTO);
			deferredResult.setResult("store/order/order_form");
		}, throwable -> {
			log.error("Error occurred while processing get OrderForm", throwable);
			deferredResult.setResult("error/500");
		});

		return deferredResult;
	}


	/**
	 * 실제 트랜잭션을 시작하기 위한 로직
	 *
	 * @param tossPaymentRequest 토스페이먼츠에서 전달해주는 내용 저장
	 * @param orderMemberId      주문자 memberId 검증을 위해 필요
	 * @param orderCode          order 를 구분하기 위한 고유한 번호
	 * @return 주문 성공페이지 이동, 주문번호, 이름 전달
	 */
	@GetMapping("/order/toss/success")
	public String getTossOrderSuccessPage(@CurrentMembers(required = false) Long memberId,
		@Valid @ModelAttribute TossPaymentRequest tossPaymentRequest,
		@RequestParam(value = "memberId", required = false) Long orderMemberId,
		@RequestParam("orderId") String orderCode, Model model) {

		if (orderService.isInvalidAccess(memberId, orderCode, orderMemberId)) {
			throw new IllegalArgumentException("부적절한 접근입니다.");
		}

		// 전달받은 orderCode, orderMemberId는 가주문서와 검증용으로 사용
		orderService.createOrder(new PaymentRequest(PaymentType.TOSS, orderMemberId, orderCode, tossPaymentRequest));
		model.addAttribute("memberDto", orderService.getSuccessView(orderCode));

		return "store/order/order_success";
	}


	@GetMapping("/order/nopay/success")
	public String getNoPayOrderSuccessPage(
		@CurrentMembers(required = false) Long memberId,
		@RequestParam(value = "memberId", required = false) Long orderMemberId,
		@RequestParam("orderId") String orderCode,
		Model model) {

		if (orderService.isInvalidAccess(memberId, orderCode, orderMemberId)) {
			throw new IllegalArgumentException("부적절한 접근입니다.");
		}

		// 전달받은 orderCode, orderMemberId는 가주문서와 검증용으로 사용
		orderService.createOrder(new PaymentRequest(PaymentType.NOPAY, orderMemberId, orderCode, null));
		model.addAttribute("memberDto", orderService.getSuccessView(orderCode));

		return "store/order/order_success";
	}

	/**
	 * 토스 결제 실패 시 fail url 리다이렉션
	 *
	 * @return
	 */
	@GetMapping("/order/toss/fail")
	public String getOrderFailPage() {
		return "store/order/order_fail";
	}

}

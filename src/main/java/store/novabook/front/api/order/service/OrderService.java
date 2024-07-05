package store.novabook.front.api.order.service;

import java.util.List;

import store.novabook.front.api.order.dto.request.TossPaymentRequest;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.order.dto.OrderViewDTO;

public interface OrderService {
	OrderViewDTO getOrder(List<BookDTO> bookDTOS, Long memberId);
	void createOrder(TossPaymentRequest request);
}

package store.novabook.front.api.order.service;

import java.util.List;

import reactor.core.publisher.Mono;
import store.novabook.front.common.aop.TimeTrace;
import store.novabook.front.store.book.dto.BookDTO;
import store.novabook.front.store.order.dto.OrderViewDTO;

public interface ReactiveOrderService {
	@TimeTrace
	Mono<OrderViewDTO> getOrder(List<BookDTO> bookDTOS, Long memberId);
}

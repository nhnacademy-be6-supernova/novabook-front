package store.novabook.front.api.order.service;

import java.util.List;

import store.novabook.front.store.book.dto.BookDTO;

public interface OrderService {
	void getOrder(List<BookDTO> bookDTOS, Long memberId);
}

package store.novabook.front.api.order.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.order.client.OrdersBookClient;
import store.novabook.front.api.order.dto.response.GetOrdersBookReviewIdResponse;
import store.novabook.front.api.order.service.OrdersBookService;
import store.novabook.front.common.response.PageResponse;

@Service
@RequiredArgsConstructor
public class OrdersBookServiceImpl implements OrdersBookService {
	private final OrdersBookClient ordersBookClient;

	@Override
	public PageResponse<GetOrdersBookReviewIdResponse> getOrdersBookReviewId(int page, int size) {

		return ordersBookClient.getOrdersBookReviewId(page, size);
	}
}

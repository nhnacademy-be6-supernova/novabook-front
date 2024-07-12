package store.novabook.front.api.order.service;

import store.novabook.front.api.order.dto.response.GetOrdersBookReviewIdResponse;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.store.order.dto.GetOrderDetailResponse;
import store.novabook.front.store.order.dto.GetOrdersBookResponse;

public interface OrdersBookService {
	PageResponse<GetOrdersBookReviewIdResponse> getOrdersBookReviewId(int page, int size);

	PageResponse<GetOrdersBookResponse> getOrdersBookAll(int page, int size);

	GetOrderDetailResponse getOrderDetail(Long ordersId);
}

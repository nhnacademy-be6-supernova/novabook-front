package store.novabook.front.api.order.service;

import store.novabook.front.api.order.dto.response.GetOrdersBookReviewIdResponse;
import store.novabook.front.common.response.PageResponse;

public interface OrdersBookService {
	PageResponse<GetOrdersBookReviewIdResponse> getOrdersBookReviewId(int page, int size);
}

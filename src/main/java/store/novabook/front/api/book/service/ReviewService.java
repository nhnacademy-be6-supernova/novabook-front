package store.novabook.front.api.book.service;

import store.novabook.front.api.order.dto.response.GetOrdersBookReviewIdResponse;
import store.novabook.front.common.response.PageResponse;

public interface ReviewService {

	PageResponse<GetOrdersBookReviewIdResponse> getOrdersBookReviewId(int page, int size);
}

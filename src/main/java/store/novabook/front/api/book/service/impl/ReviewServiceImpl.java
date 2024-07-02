package store.novabook.front.api.book.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.order.dto.response.GetOrdersBookReviewIdResponse;
import store.novabook.front.api.book.service.ReviewClient;
import store.novabook.front.api.book.service.ReviewService;
import store.novabook.front.common.response.PageResponse;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewClient reviewClient;

	@Override
	public PageResponse<GetOrdersBookReviewIdResponse> getOrdersBookReviewId(int page, int size) {
		PageResponse<GetOrdersBookReviewIdResponse> response = reviewClient.getOrdersBookReviewId(page, size);
		return response;
	}
}

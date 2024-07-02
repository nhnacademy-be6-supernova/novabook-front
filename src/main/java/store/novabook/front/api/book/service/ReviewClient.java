package store.novabook.front.api.book.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.order.dto.response.GetOrdersBookReviewIdResponse;
import store.novabook.front.common.response.PageResponse;

@FeignClient(name = "reviewClient")
public interface ReviewClient {

	@GetMapping("members/books")
	PageResponse<GetOrdersBookReviewIdResponse> getOrdersBookReviewId(@RequestParam int page,
		@RequestParam int size);
}

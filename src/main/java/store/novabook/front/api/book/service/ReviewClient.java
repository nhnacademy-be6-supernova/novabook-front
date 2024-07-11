package store.novabook.front.api.book.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.book.dto.request.CreateReviewRequest;
import store.novabook.front.api.book.dto.request.UpdateReviewRequest;
import store.novabook.front.api.book.dto.response.GetReviewListResponse;
import store.novabook.front.api.book.dto.response.GetReviewResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/reviews", contextId = "reviewClient")
public interface ReviewClient {

	@PostMapping("/{ordersBookId}")
	ApiResponse<Void> createReview(@RequestBody CreateReviewRequest createReviewRequest,
		@PathVariable Long ordersBookId);

	@GetMapping("/{reviewsId}")
	ApiResponse<GetReviewResponse> getReviewByReviewId(@PathVariable Long reviewsId);

	@GetMapping("/books/{bookId}")
	ApiResponse<GetReviewListResponse> GetReviewListByBookId(@PathVariable Long bookId);

	@PutMapping("/reviews/{reviewsId}")
	ApiResponse<Void> updateReview(@PathVariable Long reviewsId, @RequestBody UpdateReviewRequest updateReviewRequest);
}





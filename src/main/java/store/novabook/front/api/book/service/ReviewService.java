package store.novabook.front.api.book.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import store.novabook.front.api.book.dto.request.UpdateReviewRequest;
import store.novabook.front.api.book.dto.response.GetReviewListResponse;
import store.novabook.front.api.book.dto.response.GetReviewResponse;

public interface ReviewService {

	void createReview(String content, int score, List<MultipartFile> reviewImages, Long ordersBookId);

	GetReviewResponse getReviewById(Long reviewId);

	GetReviewListResponse getReviewsByBookId(Long bookId);

	void updateReview(UpdateReviewRequest request, Long reviewId);
}

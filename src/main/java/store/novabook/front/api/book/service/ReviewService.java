package store.novabook.front.api.book.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import store.novabook.front.api.book.dto.request.CreateReviewRequest;

public interface ReviewService {

	void createReview(CreateReviewRequest createReviewRequest, Long ordersBookId);

	void createReview(String content, int score, List<MultipartFile> reviewImages, Long ordersBookId);
}

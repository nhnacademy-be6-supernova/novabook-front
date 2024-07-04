package store.novabook.front.api.book.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import store.novabook.front.api.book.dto.request.CreateReviewRequest;
import store.novabook.front.api.book.dto.response.GetReviewListResponse;

public interface ReviewService {

	void createReview(String content, int score, List<MultipartFile> reviewImages, Long ordersBookId);

	GetReviewListResponse getReviewsByBookId(Long bookId);
}

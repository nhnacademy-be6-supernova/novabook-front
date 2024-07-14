package store.novabook.front.api.book.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.dto.request.CreateReviewRequest;
import store.novabook.front.api.book.dto.request.UpdateReviewRequest;
import store.novabook.front.api.book.dto.response.GetReviewListResponse;
import store.novabook.front.api.book.dto.response.GetReviewResponse;
import store.novabook.front.api.book.service.ReviewClient;
import store.novabook.front.api.book.service.ReviewService;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewClient reviewClient;

	@Override
	public void createReview(String content, int score, List<MultipartFile> reviewImages, Long ordersBookId) {
		CreateReviewRequest request = CreateReviewRequest.builder()
			.content(content)
			.score(score)
			.reviewImageDTOs(new ArrayList<>())
			.build();
		request.setReviewImageDTOs(reviewImages);
		reviewClient.createReview(request, ordersBookId);
	}

	@Override
	public GetReviewResponse getReviewById(Long reviewId) {
		return reviewClient.getReviewByReviewId(reviewId).getBody();
	}

	@Override
	public GetReviewListResponse getReviewsByBookId(Long bookId) {
		return reviewClient.GetReviewListByBookId(bookId).getBody();
	}

	@Override
	public void updateReview(UpdateReviewRequest request, Long reviewId) {
		reviewClient.updateReview(reviewId, request);
	}
}

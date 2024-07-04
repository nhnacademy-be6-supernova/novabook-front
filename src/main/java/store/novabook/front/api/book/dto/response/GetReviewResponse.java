package store.novabook.front.api.book.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record GetReviewResponse(
	String nickName,
	Long reviewId,
	Long orderBookId,
	String content,
	LocalDateTime createdAt,
	List<String> reviewImages,
	int score
) {
}

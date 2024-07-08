package store.novabook.front.api.book.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record GetBookSearchResponse(
	Long id,
	String title,
	String author,
	String publisher,
	Long price,
	Long discountPrice,
	List<String> tags,
	List<String> categories,
	float score,
	String image
) {
}

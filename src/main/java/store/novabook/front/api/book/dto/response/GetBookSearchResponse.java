package store.novabook.front.api.book.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record GetBookSearchResponse(
	Long id,
	String title,
	String author,
	String publisher,
	LocalDateTime publication_date,
	String image,
	Long price,
	Long discountPrice,
	Integer score,
	Boolean isPackaged
) {
}

package store.novabook.front.api.book.dto.response;

import lombok.Builder;

@Builder
public record GetBookLikeResponse(
	Long id,
	Long bookId,
	String title,
	String author,
	String publisher
) {
}
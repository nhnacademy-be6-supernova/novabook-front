package store.novabook.front.api.book.likes.dto;

import jakarta.validation.constraints.NotNull;

public record GetBookLikeRequest(
	@NotNull
	Long memberId,

	@NotNull
	Long bookId
) {
}

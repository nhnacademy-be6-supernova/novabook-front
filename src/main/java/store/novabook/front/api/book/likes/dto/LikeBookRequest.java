package store.novabook.front.api.book.likes.dto;

import jakarta.validation.constraints.NotNull;

public record LikeBookRequest(
	@NotNull
	Long bookId
) {
}

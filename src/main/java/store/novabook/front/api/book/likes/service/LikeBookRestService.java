package store.novabook.front.api.book.likes.service;

import store.novabook.front.api.book.likes.dto.LikeBookResponse;

public interface LikeBookRestService {

	LikeBookResponse getBookLikes(Long bookId);
	LikeBookResponse likeButton(Long bookId);
}

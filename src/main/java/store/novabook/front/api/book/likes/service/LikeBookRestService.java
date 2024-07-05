package store.novabook.front.api.book.likes.service;

import store.novabook.front.api.book.likes.dto.LikeBookRequest;
import store.novabook.front.api.book.likes.dto.LikeBookResponse;

public interface LikeBookRestService {
	LikeBookResponse createLikes(LikeBookRequest likeBookRequest);
}

package store.novabook.front.api.book.likes.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.likes.dto.LikeBookResponse;
import store.novabook.front.api.book.service.BookLikeClient;
import store.novabook.front.api.book.likes.service.LikeBookRestService;

@Service
@RequiredArgsConstructor
public class LikeBookRestServiceImpl implements LikeBookRestService {
	private final BookLikeClient bookLikeClient;

	@Override
	public LikeBookResponse getBookLikes(Long bookId) {
		return bookLikeClient.getBookLike(bookId).getBody();
	}

	@Override
	public LikeBookResponse likeButton(Long bookId) {
		return bookLikeClient.buttonLikes(bookId).getBody();
	}

}

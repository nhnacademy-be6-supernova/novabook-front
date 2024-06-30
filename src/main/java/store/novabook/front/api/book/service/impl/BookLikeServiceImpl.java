package store.novabook.front.api.book.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.BookLikeClient;
import store.novabook.front.api.book.dto.GetBookLikeResponse;
import store.novabook.front.api.book.service.LikeService;
import store.novabook.front.common.response.PageResponse;

@Service
@RequiredArgsConstructor
public class BookLikeServiceImpl implements LikeService {

	private final BookLikeClient bookLikeClient;

	@Override
	public PageResponse<GetBookLikeResponse> getBookLikeAllPage(int page, int size) {
		return bookLikeClient.getBookLikeAllPage(page, size);
	}

	@Override
	public void deleteBookLike(Long id) {
		bookLikeClient.deleteBookLike(id);
	}
}

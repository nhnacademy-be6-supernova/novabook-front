package store.novabook.front.api.book.service;

import store.novabook.front.api.book.dto.GetBookLikeResponse;
import store.novabook.front.common.response.PageResponse;

public interface LikeService {
	PageResponse<GetBookLikeResponse> getBookLikeAllPage(int page, int size);

	void deleteBookLike(Long id);
}

package store.novabook.front.api.book.service;

import store.novabook.front.api.book.dto.request.CreateBookRequest;
import store.novabook.front.api.book.dto.request.UpdateBookRequest;
import store.novabook.front.api.book.dto.response.GetBookAllResponse;
import store.novabook.front.api.book.dto.response.GetBookResponse;
import store.novabook.front.api.book.dto.response.GetBookSearchResponse;
import store.novabook.front.common.response.PageResponse;

public interface BookService {
	GetBookResponse getBookClient(Long id);

	void createBook(CreateBookRequest createBookRequest);

	PageResponse<GetBookAllResponse> getBookAll(int page, int size);

	void updateBook(UpdateBookRequest updateBookRequest);

	PageResponse<GetBookSearchResponse> getBookSearchAllPage(int page, int size, String sort);
}

package store.novabook.front.api.book.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.dto.request.CreateBookRequest;
import store.novabook.front.api.book.dto.request.UpdateBookRequest;
import store.novabook.front.api.book.dto.response.GetBookAllResponse;
import store.novabook.front.api.book.dto.response.GetBookResponse;
import store.novabook.front.api.book.dto.response.GetBookSearchResponse;
import store.novabook.front.api.book.service.BookClient;
import store.novabook.front.api.book.service.BookSearchClient;
import store.novabook.front.api.book.service.BookService;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookClient bookClient;
	private final BookSearchClient bookSearchClient;

	@Override
	public GetBookResponse getBookClient(Long id) {
		ApiResponse<GetBookResponse> book = bookClient.getBook(id);
		return book.getBody();
	}

	@Override
	public void createBook(CreateBookRequest createBookRequest) {
		bookClient.createBook(createBookRequest);
	}

	@Override
	public PageResponse<GetBookAllResponse> getBookAll(int page, int size) {
		return bookClient.getBookAll(page, size);
	}

	@Override
	public void updateBook(UpdateBookRequest updateBookRequest) {
		bookClient.updateBook(updateBookRequest);
	}

	@Override
	public PageResponse<GetBookSearchResponse> getBookSearchAllPage(String keyword, int page, int size, String sort) {

		return bookSearchClient.searchByKeyword(keyword, page, size, sort);
	}

	@Override
	public PageResponse<GetBookSearchResponse> getBookSearchCategory(String category, int page, int size, String sort) {
		return bookSearchClient.searchByCategory(category, page, size, sort);
	}
}

package store.novabook.front.api.book.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.BookClient;
import store.novabook.front.api.book.dto.CreateBookRequest;
import store.novabook.front.api.book.dto.GetBookAllResponse;
import store.novabook.front.api.book.dto.GetBookResponse;
import store.novabook.front.api.book.dto.UpdateBookRequest;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookClient bookClient;

	public GetBookResponse getBookClient(Long id) {
		ApiResponse<GetBookResponse> book = bookClient.getBook(id);
		return book.getBody();
	}

	public void createBook(CreateBookRequest createBookRequest) {
		bookClient.createBook(createBookRequest);
	}

	public PageResponse<GetBookAllResponse> getBookAll(int page, int size) {
		return bookClient.getBookAll(page, size);
	}

	public void updateBook(UpdateBookRequest updateBookRequest) {
		bookClient.updateBook(updateBookRequest);
	}
}

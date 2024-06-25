package store.novabook.front.api.book.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.ApiResponse;
import store.novabook.front.api.book.BookClient;
import store.novabook.front.api.book.dto.GetBookResponse;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookClient bookClient;

	public GetBookResponse getBookClient(Long id) {
		ApiResponse<GetBookResponse> book = bookClient.getBook(id);

		GetBookResponse getBookResponse = book.getBody();
		return getBookResponse;
	}
}

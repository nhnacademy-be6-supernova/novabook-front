package store.novabook.front.admin.book;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.dto.request.CreateBookRequest;
import store.novabook.front.api.book.dto.request.UpdateBookRequest;
import store.novabook.front.api.book.service.BookService;
import store.novabook.front.common.handler.HandleWithAlert;

@HandleWithAlert
@RequestMapping("/api/v1/front/admin/books")
@RestController
@RequiredArgsConstructor
public class AdminBookRestController {
	private final BookService bookService;

	@PostMapping("/book/form")
	public ResponseEntity<Object> createBook(@RequestBody CreateBookRequest createBookRequest) {
		bookService.createBook(createBookRequest);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/book/update")
	public ResponseEntity<Object> updateBook(@RequestBody UpdateBookRequest updateBookRequest) {
		bookService.updateBook(updateBookRequest);
		return ResponseEntity.ok().build();
	}
}

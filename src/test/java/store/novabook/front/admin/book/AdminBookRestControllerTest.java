package store.novabook.front.admin.book;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import store.novabook.front.api.book.dto.request.CreateBookRequest;
import store.novabook.front.api.book.dto.request.UpdateBookRequest;
import store.novabook.front.api.book.service.BookService;

@SpringJUnitConfig
class AdminBookRestControllerTest {

	@Mock
	private BookService bookService;

	@InjectMocks
	private AdminBookRestController adminBookRestController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createBook_ShouldCallServiceAndReturnOk() {

		CreateBookRequest createBookRequest = new CreateBookRequest(
			1L,
			"1234567890123",
			"Sample Book Title",
			"This is a brief description of the book.",
			"This is a detailed description of the book, covering all aspects and features.",
			"Author Name",
			"Publisher Name",
			LocalDateTime.now(),
			100,
			20000L,
			15000L,
			true,
			List.of(1L, 2L),
			List.of(1L, 2L),
			"image/path.jpg"
		);

		ResponseEntity<Object> response = adminBookRestController.createBook(createBookRequest);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void updateBook_ShouldCallServiceAndReturnOk() {
		UpdateBookRequest updateBookRequest = UpdateBookRequest.builder()
			.id(1L)
			.bookStatusId(2L)
			.inventory(100)
			.price(15000L)
			.discountPrice(12000L)
			.isPackaged(true)
			.build();
		ResponseEntity<Object> response = adminBookRestController.updateBook(updateBookRequest);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}

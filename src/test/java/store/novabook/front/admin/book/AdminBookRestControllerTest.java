package store.novabook.front.admin.book;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

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
public class AdminBookRestControllerTest {

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
			1L, // bookStatusId
			"1234567890123", // isbn
			"Sample Book Title", // title
			"This is a brief description of the book.", // description
			"This is a detailed description of the book, covering all aspects and features.", // descriptionDetail
			"Author Name", // author
			"Publisher Name", // publisher
			LocalDateTime.now(), // publicationDate
			100, // inventory
			20000L, // price
			15000L, // discountPrice
			true, // isPackaged
			List.of(1L, 2L), // tags
			List.of(1L, 2L), // categories
			"image/path.jpg" // image
		);

		// Act
		ResponseEntity<Object> response = adminBookRestController.createBook(createBookRequest);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void updateBook_ShouldCallServiceAndReturnOk() {
		// Arrange
		UpdateBookRequest updateBookRequest = UpdateBookRequest.builder()
			.id(1L)
			.bookStatusId(2L)
			.inventory(100)
			.price(15000L)
			.discountPrice(12000L)
			.isPackaged(true)
			.build();
		// Act
		ResponseEntity<Object> response = adminBookRestController.updateBook(updateBookRequest);

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}

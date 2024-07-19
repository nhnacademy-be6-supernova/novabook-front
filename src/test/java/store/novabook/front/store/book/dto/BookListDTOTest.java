package store.novabook.front.store.book.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class BookListDTOTest {

	@Test
	void testBookListDTO() {

		BookDTO book1 = BookDTO.builder()
			.id(1L)
			.imageUrl("http://example.com/image.jpg")
			.name("Example Book")
			.price(1500L)
			.quantity(10L)
			.discount(200L)
			.isPackage(false)
			.build();

		List<BookDTO> bookList = List.of(book1);

		BookListDTO bookListDTO = BookListDTO.builder()
			.bookDTOS(bookList)
			.build();

		assertNotNull(bookListDTO);
		assertNotNull(bookListDTO.bookDTOS());
		assertEquals(1, bookListDTO.bookDTOS().size());
		assertEquals(book1, bookListDTO.bookDTOS().getFirst());
	}

	@Test
	void testEmptyBookListDTO() {
		BookListDTO bookListDTO = BookListDTO.builder()
			.bookDTOS(Collections.emptyList())
			.build();

		assertNotNull(bookListDTO);
		assertNotNull(bookListDTO.bookDTOS());
		assertEquals(0, bookListDTO.bookDTOS().size());
	}
}

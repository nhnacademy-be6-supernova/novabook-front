package store.novabook.front.store.book.dto;

import java.util.List;


public record BookListDTO(
	List<BookDTO> bookDTOS
) {
}

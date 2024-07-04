package store.novabook.front.store.book.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record BookListDTO(
	List<BookDTO> bookDTOS
) {}
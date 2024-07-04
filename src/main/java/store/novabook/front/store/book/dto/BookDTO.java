package store.novabook.front.store.book.dto;

import lombok.Builder;

@Builder
public record BookDTO(
	Long id,
	String imageUrl,
	String name,
	long price,
	long quantity,
	long discount,
	boolean isPackage
) {
}
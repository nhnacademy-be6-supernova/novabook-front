package store.novabook.front.api.book.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record GetBookResponse(
	Long id,
	String isbn,
	String title,
	String bookIndex,
	String description,
	String descriptionDetail,
	String author,
	String publisher,
	LocalDateTime publicationDate,
	int inventory,
	Long price,
	Long discountPrice,
	boolean isPackaged,
	String image,
	List<String> tags,
	List<String> categories,
	int likes,
	int score
) {
}

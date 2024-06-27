package store.novabook.front.api.book.dto;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public record CreateBookRequest(
	Long bookStatusId,
	String isbn,
	String title,
	String description,
	String descriptionDetail,
	String author,
	String publisher,
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	LocalDateTime publicationDate,
	Integer inventory,
	Long price,
	Long discountPrice,
	Boolean isPackaged,
	List<Long> tags,
	Long categoryId,
	String image
) {
}

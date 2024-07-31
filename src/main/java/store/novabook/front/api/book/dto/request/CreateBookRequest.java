package store.novabook.front.api.book.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateBookRequest(
	@NotNull
	Long bookStatusId,

	@NotBlank
	@Size(min = 10, max = 13, message = "ISBN should be between 10 and 13 characters")
	String isbn,

	@NotBlank
	@Size(max = 255, message = "Title should not exceed 255 characters")
	String title,

	@NotBlank
	@Size(max = 1000, message = "Description should not exceed 1000 characters")
	String description,

	@NotBlank
	@Size(max = 2000, message = "Detailed description should not exceed 2000 characters")
	String descriptionDetail,

	@NotBlank
	@Size(max = 255, message = "Author name should not exceed 255 characters")
	String author,

	@NotBlank
	@Size(max = 255, message = "Publisher name should not exceed 255 characters")
	String publisher,

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@NotNull
	LocalDateTime publicationDate,

	@NotNull
	Integer inventory,

	@NotNull
	Long price,

	@NotNull
	@Max(value = Long.BYTES, message = "DiscountPrice bigger than price")
	@Positive
	Long discountPrice,

	@NotNull
	Boolean isPackaged,

	List<Long> tags,

	List<Long> categories,

	String image

) {
	@AssertTrue(message = "DiscountPrice bigger than price")
	public boolean isValid() {
		return price > discountPrice;
	}
}

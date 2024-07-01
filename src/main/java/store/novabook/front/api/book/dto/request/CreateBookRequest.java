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
import jakarta.validation.constraints.Size;

public record CreateBookRequest(
	@NotNull(message = "책 상태 ID는 필수 입력 항목입니다.")
	Long bookStatusId,

	@NotBlank(message = "ISBN은 필수 입력 항목입니다.")
	@Size(min = 10, max = 13, message = "ISBN은 10자에서 13자 사이여야 합니다.")
	String isbn,

	@NotBlank(message = "제목은 필수 입력 항목입니다.")
	@Size(max = 255, message = "제목은 255자를 초과할 수 없습니다.")
	String title,

	@NotBlank(message = "설명은 필수 입력 항목입니다.")
	@Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다.")
	String description,

	@NotBlank(message = "상세 설명은 필수 입력 항목입니다.")
	@Size(max = 2000, message = "상세 설명은 2000자를 초과할 수 없습니다.")
	String descriptionDetail,

	@NotBlank(message = "저자 이름은 필수 입력 항목입니다.")
	@Size(max = 255, message = "저자 이름은 255자를 초과할 수 없습니다.")
	String author,

	@NotBlank(message = "출판사 이름은 필수 입력 항목입니다.")
	@Size(max = 255, message = "출판사 이름은 255자를 초과할 수 없습니다.")
	String publisher,

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@NotNull(message = "출판일은 필수 입력 항목입니다.")
	LocalDateTime publicationDate,

	@NotNull(message = "재고는 필수 입력 항목입니다.")
	Integer inventory,

	@NotNull(message = "가격은 필수 입력 항목입니다.")
	Long price,

	@NotNull(message = "할인가는 필수 입력 항목입니다.")
	@Max(value = Long.BYTES, message = "할인가는 원가보다 클 수 없습니다.")
	Long discountPrice,

	@NotNull(message = "포장 여부는 필수 입력 항목입니다.")
	Boolean isPackaged,

	List<Long> tags,

	@NotNull(message = "카테고리 ID는 필수 입력 항목입니다.")
	Long categoryId,

	String image

) {
	@AssertTrue(message = "할인가는 원가보다 클 수 없습니다.")
	public boolean isValid() {
		return price > discountPrice;
	}
}

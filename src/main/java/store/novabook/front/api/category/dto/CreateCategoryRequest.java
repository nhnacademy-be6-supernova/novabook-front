package store.novabook.front.api.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
	Long topCategoryId,
	@NotBlank(message = "카테고리 이름은 필수 입력값 입니다.")
	@Size(max = 50, message = "카테고리 이름은 최대 50자 까지 입력 가능합니다.")
	String name
) {
}

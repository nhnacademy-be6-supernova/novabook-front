package store.novabook.front.api.category.dto;

public record CreateCategoryRequest(
	Long topCategoryId,
	// @NotBlank(message = "카테고리 이름은 필수 입력값 입니다.")
	String name
) {
}

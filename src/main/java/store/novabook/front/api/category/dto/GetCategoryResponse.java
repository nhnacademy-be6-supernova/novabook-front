package store.novabook.front.api.category.dto;

import java.util.List;

import store.novabook.front.api.category.domain.Category;

public record GetCategoryResponse(Long id, String name, List<SubCategoryDTO> sub) {
	public static GetCategoryResponse fromEntity(Category category, List<SubCategoryDTO> sub) {
		return new GetCategoryResponse(category.getId(), category.getName(), sub);
	}
}

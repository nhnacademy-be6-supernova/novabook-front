package store.novabook.front.api.category.dto;

import store.novabook.front.api.category.domain.Category;

public record GetCategoryResponse(Long id, String name) {
	public static GetCategoryResponse fromEntity(Category category) {
		return new GetCategoryResponse(category.getId(), category.getName());
	}

}

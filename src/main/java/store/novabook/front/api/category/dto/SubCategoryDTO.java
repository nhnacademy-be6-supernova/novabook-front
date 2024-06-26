package store.novabook.front.api.category.dto;

import store.novabook.front.api.category.domain.Category;

public record SubCategoryDTO(Long id, String name) {
	public static SubCategoryDTO fromEntity(Category category) {
		return new SubCategoryDTO(category.getId(), category.getName());
	}
}

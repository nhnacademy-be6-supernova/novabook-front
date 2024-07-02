package store.novabook.front.api.category.dto.response;

import java.util.List;

import store.novabook.front.api.category.domain.Category;
import store.novabook.front.api.category.dto.SubCategoryDTO;

public record GetCategoryResponse(Long id, String name, List<SubCategoryDTO> sub) {
	public static GetCategoryResponse fromEntity(Category category, List<SubCategoryDTO> sub) {
		return new GetCategoryResponse(category.getId(), category.getName(), sub);
	}
}

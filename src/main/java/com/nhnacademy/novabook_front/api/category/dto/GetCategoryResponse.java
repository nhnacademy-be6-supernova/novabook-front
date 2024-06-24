package com.nhnacademy.novabook_front.api.category.dto;

import com.nhnacademy.novabook_front.api.category.domain.Category;

public record GetCategoryResponse(Long id, String name) {
	public static GetCategoryResponse fromEntity(Category category) {
		return new GetCategoryResponse(category.getId(), category.getName());
	}

}

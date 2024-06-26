package store.novabook.front.api.category.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.category.dto.CreateCategoryRequest;
import store.novabook.front.api.category.dto.CreateCategoryResponse;
import store.novabook.front.api.category.dto.GetCategoryListResponse;
import store.novabook.front.api.category.dto.GetCategoryResponse;

public interface CategoryService {

	CreateCategoryResponse createCategory(@RequestBody CreateCategoryRequest category);
	GetCategoryResponse getCategory(@PathVariable Long id);
	GetCategoryListResponse getCategoryAll();
	void deleteCategory(@PathVariable Long id);
}

package store.novabook.front.api.category.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.category.dto.request.CreateCategoryRequest;
import store.novabook.front.api.category.dto.response.CreateCategoryResponse;
import store.novabook.front.api.category.dto.response.GetCategoryListResponse;
import store.novabook.front.api.category.dto.response.GetCategoryResponse;

public interface CategoryService {

	CreateCategoryResponse createCategory(@RequestBody CreateCategoryRequest category);
	GetCategoryResponse getCategory(@PathVariable Long id);
	GetCategoryListResponse getCategoryAll();
	void deleteCategory(@PathVariable Long id);
}

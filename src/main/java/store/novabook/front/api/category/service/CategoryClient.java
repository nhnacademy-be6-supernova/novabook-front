package store.novabook.front.api.category.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.category.dto.request.CreateCategoryRequest;
import store.novabook.front.api.category.dto.response.CreateCategoryResponse;
import store.novabook.front.api.category.dto.response.GetCategoryListResponse;
import store.novabook.front.api.category.dto.response.GetCategoryResponse;
import store.novabook.front.common.response.ApiResponse;


@FeignClient(name = "categoryClient")
public interface CategoryClient {
	@PostMapping
	ApiResponse<CreateCategoryResponse> createCategory(@RequestBody CreateCategoryRequest category);

	@GetMapping("/{id}")
	ApiResponse<GetCategoryResponse> getCategory(@PathVariable Long id);

	@GetMapping
	ApiResponse<GetCategoryListResponse> getCategoryAll();

	@DeleteMapping("/{id}")
	ApiResponse<Void> deleteCategory(@PathVariable Long id);
}

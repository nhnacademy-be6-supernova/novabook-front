package store.novabook.front.api.category;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.api.category.dto.CreateCategoryRequest;
import store.novabook.front.api.category.dto.CreateCategoryResponse;
import store.novabook.front.api.category.dto.GetCategoryListResponse;
import store.novabook.front.api.category.dto.GetCategoryResponse;

@FeignClient(name = "categoryClient", url = "http://localhost:8090/api/v1/store/categories")
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

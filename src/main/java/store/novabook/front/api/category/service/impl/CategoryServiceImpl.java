package store.novabook.front.api.category.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.ApiResponse;
import store.novabook.front.api.category.CategoryClient;
import store.novabook.front.api.category.dto.CreateCategoryRequest;
import store.novabook.front.api.category.dto.CreateCategoryResponse;
import store.novabook.front.api.category.dto.GetCategoryListResponse;
import store.novabook.front.api.category.dto.GetCategoryResponse;
import store.novabook.front.api.category.service.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final CategoryClient categoryClient;

	@Override
	public GetCategoryListResponse getCategoryAll() {
		ApiResponse<GetCategoryListResponse> apiResponse = categoryClient.getCategoryAll();
		return apiResponse.getBody();
	}

	@Override
	public void deleteCategory(Long id) {
		ApiResponse<Void> apiResponse = categoryClient.deleteCategory(id);

	}

	@Override
	public CreateCategoryResponse createCategory(CreateCategoryRequest category) {
		ApiResponse<CreateCategoryResponse> apiResponse = categoryClient.createCategory(category);
		return apiResponse.getBody();
	}

	@Override
	public GetCategoryResponse getCategory(Long id) {
		ApiResponse<GetCategoryResponse> apiResponse = categoryClient.getCategory(id);
		return apiResponse.getBody();
	}
}

package store.novabook.front.api.category.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.category.dto.request.CreateCategoryRequest;
import store.novabook.front.api.category.dto.response.CreateCategoryResponse;
import store.novabook.front.api.category.dto.response.GetCategoryListResponse;
import store.novabook.front.api.category.dto.response.GetCategoryResponse;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.api.category.service.CategoryService;
import store.novabook.front.common.response.ApiResponse;

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

package com.nhnacademy.novabook_front.api.category.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nhnacademy.novabook_front.api.ApiResponse;
import com.nhnacademy.novabook_front.api.category.CategoryClient;
import com.nhnacademy.novabook_front.api.category.dto.CreateCategoryRequest;
import com.nhnacademy.novabook_front.api.category.dto.CreateCategoryResponse;
import com.nhnacademy.novabook_front.api.category.dto.GetCategoryListResponse;
import com.nhnacademy.novabook_front.api.category.dto.GetCategoryResponse;
import com.nhnacademy.novabook_front.api.category.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final CategoryClient categoryClient;


	@Override
	public List<GetCategoryListResponse> getCategoryAll() {
		ApiResponse<List<GetCategoryListResponse>> apiResponse = categoryClient.getCategoryAll();
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

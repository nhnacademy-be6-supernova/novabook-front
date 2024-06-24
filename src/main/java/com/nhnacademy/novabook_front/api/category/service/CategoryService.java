package com.nhnacademy.novabook_front.api.category.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.novabook_front.api.category.dto.CreateCategoryRequest;
import com.nhnacademy.novabook_front.api.category.dto.CreateCategoryResponse;
import com.nhnacademy.novabook_front.api.category.dto.GetCategoryListResponse;
import com.nhnacademy.novabook_front.api.category.dto.GetCategoryResponse;

public interface CategoryService {

	CreateCategoryResponse createCategory(@RequestBody CreateCategoryRequest category);
	GetCategoryResponse getCategory(@PathVariable Long id);
	List<GetCategoryListResponse> getCategoryAll();
	void deleteCategory(@PathVariable Long id);
}

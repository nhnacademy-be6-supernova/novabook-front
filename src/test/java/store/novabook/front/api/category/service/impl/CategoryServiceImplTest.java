package store.novabook.front.api.category.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.category.dto.SubCategoryDTO;
import store.novabook.front.api.category.dto.request.CreateCategoryRequest;
import store.novabook.front.api.category.dto.response.CreateCategoryResponse;
import store.novabook.front.api.category.dto.response.GetCategoryListResponse;
import store.novabook.front.api.category.dto.response.GetCategoryResponse;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.common.response.ApiResponse;

class CategoryServiceImplTest {

	@Mock
	private CategoryClient categoryClient;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetCategoryAll() {
		SubCategoryDTO subCategoryDTO = new SubCategoryDTO(1L, "Sub Category Name");
		GetCategoryResponse getCategoryResponse = new GetCategoryResponse(1L, "Category Name",
			Collections.singletonList(subCategoryDTO));

		GetCategoryListResponse expectedResponse = new GetCategoryListResponse(
			Collections.singletonList(getCategoryResponse));

		when(categoryClient.getCategoryAll()).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		GetCategoryListResponse actualResponse = categoryService.getCategoryAll();

		assertEquals(expectedResponse, actualResponse);

		verify(categoryClient, times(1)).getCategoryAll();
	}

	@Test
	void testCreateCategory() {

		CreateCategoryRequest request = CreateCategoryRequest.builder()
			.topCategoryId(1L)
			.name("새 카테고리 이름")
			.build();
		CreateCategoryResponse expectedResponse = new CreateCategoryResponse(1L);

		when(categoryClient.createCategory(request)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		CreateCategoryResponse actualResponse = categoryService.createCategory(request);

		assertEquals(expectedResponse, actualResponse);

		verify(categoryClient, times(1)).createCategory(request);
	}

	@Test
	void testGetCategory() {
		Long categoryId = 1L;
		SubCategoryDTO subCategoryDTO = new SubCategoryDTO(1L, "Sub Category Name");
		GetCategoryResponse getCategoryResponse = new GetCategoryResponse(1L, "Category Name",
			Collections.singletonList(subCategoryDTO));
		when(categoryClient.getCategory(categoryId)).thenReturn(new ApiResponse<>("SUCCESS", true, getCategoryResponse));

		GetCategoryResponse actualResponse = categoryService.getCategory(categoryId);

		assertEquals(getCategoryResponse, actualResponse);

		verify(categoryClient, times(1)).getCategory(categoryId);
	}
}

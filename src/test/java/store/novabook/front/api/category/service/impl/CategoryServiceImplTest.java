package store.novabook.front.api.category.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import store.novabook.front.api.category.dto.SubCategoryDTO;
import store.novabook.front.api.category.dto.request.CreateCategoryRequest;
import store.novabook.front.api.category.dto.response.CreateCategoryResponse;
import store.novabook.front.api.category.dto.response.GetCategoryListResponse;
import store.novabook.front.api.category.dto.response.GetCategoryResponse;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.common.response.ApiResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Collections;

public class CategoryServiceImplTest {

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

		// Given
		GetCategoryListResponse expectedResponse = new GetCategoryListResponse(
			Collections.singletonList(getCategoryResponse)); // Adjust as per your response structure

		// Mocking the service method
		when(categoryClient.getCategoryAll()).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		GetCategoryListResponse actualResponse = categoryService.getCategoryAll();

		// Then
		assertEquals(expectedResponse, actualResponse);

		// Verify that getCategoryAll method of categoryClient is called exactly once
		verify(categoryClient, times(1)).getCategoryAll();
	}

	@Test
	void testCreateCategory() {

		CreateCategoryRequest request = CreateCategoryRequest.builder()
			.topCategoryId(1L)
			.name("새 카테고리 이름")
			.build();
		CreateCategoryResponse expectedResponse = new CreateCategoryResponse(1L);

		// Mocking the service method
		when(categoryClient.createCategory(request)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		CreateCategoryResponse actualResponse = categoryService.createCategory(request);

		// Then
		assertEquals(expectedResponse, actualResponse);

		// Verify that createCategory method of categoryClient is called exactly once with the request object
		verify(categoryClient, times(1)).createCategory(request);
	}

	@Test
	void testGetCategory() {
		// Given
		Long categoryId = 1L;
		SubCategoryDTO subCategoryDTO = new SubCategoryDTO(1L, "Sub Category Name");
		GetCategoryResponse getCategoryResponse = new GetCategoryResponse(1L, "Category Name",
			Collections.singletonList(subCategoryDTO));
		// Mocking the service method
		when(categoryClient.getCategory(categoryId)).thenReturn(new ApiResponse<>("SUCCESS", true, getCategoryResponse));

		// When
		GetCategoryResponse actualResponse = categoryService.getCategory(categoryId);

		// Then
		assertEquals(getCategoryResponse, actualResponse);

		// Verify that getCategory method of categoryClient is called exactly once with the categoryId
		verify(categoryClient, times(1)).getCategory(categoryId);
	}
}

package store.novabook.front.api.category.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import store.novabook.front.api.category.dto.SubCategoryDTO;
import store.novabook.front.api.category.dto.response.DeleteResponse;
import store.novabook.front.api.category.dto.response.GetCategoryListResponse;
import store.novabook.front.api.category.dto.response.GetCategoryResponse;
import store.novabook.front.api.category.service.CategoryRestService;
import store.novabook.front.api.category.service.CategoryService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Collections;

public class CategoryRestControllerTest {

	@Mock
	private CategoryRestService categoryRestService;

	@Mock
	private CategoryService categoryService;

	@InjectMocks
	private CategoryRestController categoryRestController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testIsRegistered() {
		// Given
		Long categoryId = 1L;
		DeleteResponse expectedResponse = new DeleteResponse(true);

		// Mocking the service method
		when(categoryRestService.delete(categoryId)).thenReturn(expectedResponse);

		// When
		ResponseEntity<DeleteResponse> responseEntity = categoryRestController.isRegistered(categoryId);

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedResponse, responseEntity.getBody());

		// Verify that delete method of categoryRestService is called exactly once with categoryId
		verify(categoryRestService, times(1)).delete(categoryId);
	}

	@Test
	void testGetCategory() {
		// Given
		SubCategoryDTO subCategoryDTO = new SubCategoryDTO(1L, "Sub Category Name");
		GetCategoryResponse getCategoryResponse = new GetCategoryResponse(1L, "Category Name",
			Collections.singletonList(subCategoryDTO));

		GetCategoryListResponse expectedResponse = new GetCategoryListResponse(
			Collections.singletonList(getCategoryResponse)); // Adjust as per your response structure

		// Mocking the service method
		when(categoryService.getCategoryAll()).thenReturn(expectedResponse);

		// When
		ResponseEntity<Object> responseEntity = categoryRestController.getCategory();

		// Then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedResponse, responseEntity.getBody());

		// Verify that getCategoryAll method of categoryService is called exactly once
		verify(categoryService, times(1)).getCategoryAll();
	}
}

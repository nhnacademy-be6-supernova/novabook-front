package store.novabook.front.api.category.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

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

class CategoryRestControllerTest {

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
		Long categoryId = 1L;
		DeleteResponse expectedResponse = new DeleteResponse(true);

		when(categoryRestService.delete(categoryId)).thenReturn(expectedResponse);

		ResponseEntity<DeleteResponse> responseEntity = categoryRestController.isRegistered(categoryId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedResponse, responseEntity.getBody());

		verify(categoryRestService, times(1)).delete(categoryId);
	}

	@Test
	void testGetCategory() {
		SubCategoryDTO subCategoryDTO = new SubCategoryDTO(1L, "Sub Category Name");
		GetCategoryResponse getCategoryResponse = new GetCategoryResponse(1L, "Category Name",
			Collections.singletonList(subCategoryDTO));

		GetCategoryListResponse expectedResponse = new GetCategoryListResponse(
			Collections.singletonList(getCategoryResponse));

		when(categoryService.getCategoryAll()).thenReturn(expectedResponse);

		ResponseEntity<Object> responseEntity = categoryRestController.getCategory();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedResponse, responseEntity.getBody());

		verify(categoryService, times(1)).getCategoryAll();
	}
}

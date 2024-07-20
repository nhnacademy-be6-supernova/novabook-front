package store.novabook.front.api.category.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.category.dto.response.DeleteResponse;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.common.response.ApiResponse;

class CategoryRestServiceImplTest {

	@Mock
	private CategoryClient categoryClient;

	@InjectMocks
	private CategoryRestServiceImpl categoryRestService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testDeleteCategory() {
		Long categoryId = 1L;
		DeleteResponse expectedResponse = new DeleteResponse(true);

		when(categoryClient.delete(categoryId)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		DeleteResponse actualResponse = categoryRestService.delete(categoryId);

		assertEquals(expectedResponse, actualResponse);

		verify(categoryClient, times(1)).delete(categoryId);
	}
}

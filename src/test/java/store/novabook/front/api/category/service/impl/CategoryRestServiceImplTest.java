package store.novabook.front.api.category.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import store.novabook.front.api.category.dto.response.DeleteResponse;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.common.response.ApiResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryRestServiceImplTest {

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
		// Given
		Long categoryId = 1L;
		DeleteResponse expectedResponse = new DeleteResponse(true);

		// Mocking the service method
		when(categoryClient.delete(categoryId)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		DeleteResponse actualResponse = categoryRestService.delete(categoryId);

		// Then
		assertEquals(expectedResponse, actualResponse);

		// Verify that delete method of categoryClient is called exactly once with categoryId
		verify(categoryClient, times(1)).delete(categoryId);
	}
}

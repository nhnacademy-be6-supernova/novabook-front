package store.novabook.front.admin.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.ui.Model;

import store.novabook.front.api.book.dto.request.UpdateBookRequest;
import store.novabook.front.api.book.dto.response.GetBookAllResponse;
import store.novabook.front.api.book.service.BookService;
import store.novabook.front.api.category.domain.Category;
import store.novabook.front.api.category.dto.SubCategoryDTO;
import store.novabook.front.api.category.dto.response.GetCategoryListResponse;
import store.novabook.front.api.category.dto.response.GetCategoryResponse;
import store.novabook.front.api.category.service.CategoryService;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.tag.service.TagService;
import store.novabook.front.common.response.PageResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringJUnitConfig
public class AdminBookControllerTest {

	@Mock
	private BookService bookService;

	@Mock
	private CategoryService categoryService;

	@Mock
	private TagService tagService;

	@InjectMocks
	private AdminBookController adminBookController;

	@Mock
	private Model model;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getBookAll_ShouldAddBooksToModelAndReturnViewName() {

		GetBookAllResponse getBookAllResponse = GetBookAllResponse.builder()
			.id(1L)
			.bookStatusId(2L)
			.title("Sample Book Title")
			.inventory(10)
			.price(20000L)
			.discountPrice(18000L)
			.isPackaged(true)
			.build();

		List<GetBookAllResponse> data = new ArrayList<>();
		data.add(getBookAllResponse);

		// Arrange
		PageResponse<GetBookAllResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);
		when(bookService.getBookAll(anyInt(), anyInt())).thenReturn(expectedResponse);

		// Act
		String viewName = adminBookController.getBookAll(model, 0, 10);

		// Assert
		assertEquals("admin/book/book_list", viewName);
	}

	@Test
	void getBookForm_ShouldAddCategoriesAndTagsToModelAndReturnViewName() {

		Category category = Category.builder()
			.id(1L)
			.topCategory(null) // Assuming this is a top-level category with no parent
			.name("Fiction")
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
		List<SubCategoryDTO> subCategories = List.of(
			new SubCategoryDTO(1L, "Science Fiction"),
			new SubCategoryDTO(2L, "Fantasy")
		);

		GetCategoryResponse getCategoryResponse = GetCategoryResponse.fromEntity(category, subCategories);
		// Arrange
		GetCategoryListResponse getCategoryListResponse = new GetCategoryListResponse(List.of(getCategoryResponse));
		when(categoryService.getCategoryAll()).thenReturn(getCategoryListResponse);
		when(tagService.getTagList()).thenReturn(Collections.emptyList());

		// Act
		String viewName = adminBookController.getBookForm(model);

		// Assert
		assertEquals("admin/book/book_form", viewName);
	}

	@Test
	void updateBook_ShouldCallServiceAndRedirect() {

		UpdateBookRequest updateBookRequest = UpdateBookRequest.builder()
			.id(1L)
			.bookStatusId(2L)
			.inventory(100)
			.price(15000L)
			.discountPrice(12000L)
			.isPackaged(true)
			.build();

		// Act
		String viewName = adminBookController.updateBook(updateBookRequest);

		// Assert
		assertEquals("redirect:/admin/books", viewName);
	}
}

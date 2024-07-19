package store.novabook.front.admin.category;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.category.domain.Category;
import store.novabook.front.api.category.dto.SubCategoryDTO;
import store.novabook.front.api.category.dto.request.CreateCategoryRequest;
import store.novabook.front.api.category.dto.response.GetCategoryListResponse;
import store.novabook.front.api.category.dto.response.GetCategoryResponse;
import store.novabook.front.api.category.service.CategoryService;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@WebMvcTest(AdminCategoryController.class)
class AdminCategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new AdminCategoryController(categoryService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void testGetCategories() throws Exception {

		Category category = Category.builder()
			.id(1L)
			.topCategory(null)
			.name("Fiction")
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		SubCategoryDTO subCategoryDTO1 = new SubCategoryDTO(2L, "Sub Category 1");
		SubCategoryDTO subCategoryDTO2 = new SubCategoryDTO(3L, "Sub Category 2");
		List<SubCategoryDTO> subCategories = List.of(subCategoryDTO1, subCategoryDTO2);

		GetCategoryResponse getCategoryResponse = GetCategoryResponse.fromEntity(category, subCategories);
		GetCategoryListResponse getCategoryListResponse = GetCategoryListResponse.builder()
			.categories(List.of(getCategoryResponse))
			.build();

		when(categoryService.getCategoryAll()).thenReturn(getCategoryListResponse);

		mockMvc.perform(get("/admin/categories"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/category/category_list"))
			.andExpect(model().attributeExists("categories"))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testCreateCategory() throws Exception {

		mockMvc.perform(post("/admin/categories")
				.param("topCategoryId", "1")
				.param("name", "New Category"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", "/admin/categories"))
			.andDo(MockMvcResultHandlers.print());

		verify(categoryService, times(1)).createCategory(any(CreateCategoryRequest.class));
	}
}

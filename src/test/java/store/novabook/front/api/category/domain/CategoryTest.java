package store.novabook.front.api.category.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class CategoryTest {

	@Test
	void testCategoryConstructorAndGetters() {
		Long id = 1L;
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime updatedAt = LocalDateTime.now();

		Category topCategory = Category.builder()
			.id(2L)
			.topCategory(null)
			.name("Category Top")
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.build();
		String name = "Category Top";
		Category category = Category.builder()
			.id(1L)
			.topCategory(topCategory)
			.name("Category Top")
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.build();
		assertThat(category.getId()).isEqualTo(id);
		assertThat(category.getTopCategory()).isEqualTo(topCategory);
		assertThat(category.getName()).isEqualTo(name);
		assertThat(category.getCreatedAt()).isEqualTo(createdAt);
		assertThat(category.getUpdatedAt()).isEqualTo(updatedAt);
	}
}

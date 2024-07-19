package store.novabook.front.api.category.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class CategoryTest {

	@Test
	void testCategoryConstructorAndGetters() {
		// Given
		Long id = 1L;
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime updatedAt = LocalDateTime.now();

		Category topCategory = Category.builder()
			.id(2L)
			.topCategory(null) // Assuming null for a top-level category, adjust as needed
			.name("Category Top")
			.createdAt(createdAt) // Use actual creation time if available
			.updatedAt(updatedAt) // Use actual update time if available
			.build();
		String name = "Category Top";
		Category category = Category.builder()
			.id(1L)
			.topCategory(topCategory) // Assuming null for a top-level category, adjust as needed
			.name("Category Top")
			.createdAt(createdAt) // Use actual creation time if available
			.updatedAt(updatedAt) // Use actual update time if available
			.build();
		// Then
		assertThat(category.getId()).isEqualTo(id);
		assertThat(category.getTopCategory()).isEqualTo(topCategory);
		assertThat(category.getName()).isEqualTo(name);
		assertThat(category.getCreatedAt()).isEqualTo(createdAt);
		assertThat(category.getUpdatedAt()).isEqualTo(updatedAt);
	}
}

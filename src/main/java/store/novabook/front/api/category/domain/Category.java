package store.novabook.front.api.category.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Category {
	private Long id;
	private Category topCategory;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

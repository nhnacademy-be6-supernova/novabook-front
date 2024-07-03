package store.novabook.front.api.category.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface CategoryRestService {
	void deleteCategory(@PathVariable Long id);
}

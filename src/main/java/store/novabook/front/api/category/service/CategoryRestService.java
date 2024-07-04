package store.novabook.front.api.category.service;

import org.springframework.web.bind.annotation.PathVariable;

import store.novabook.front.api.category.dto.response.DeleteResponse;

public interface CategoryRestService {
	DeleteResponse delete(@PathVariable Long id);
}

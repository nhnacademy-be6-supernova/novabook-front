package store.novabook.front.api.category.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.api.category.service.CategoryRestService;

@Service
@RequiredArgsConstructor
public class CategoryRestServiceImpl implements CategoryRestService {
	private final CategoryClient categoryClient;

	@Override
	public void deleteCategory(Long id) {
		categoryClient.deleteCategory(id).getBody();
	}
}

package store.novabook.front.api.category.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.category.dto.response.DeleteResponse;
import store.novabook.front.api.category.service.CategoryClient;
import store.novabook.front.api.category.service.CategoryRestService;

@Service
@RequiredArgsConstructor
public class CategoryRestServiceImpl implements CategoryRestService {
	private final CategoryClient categoryClient;

	@Override
	public DeleteResponse delete(Long id) {
		return categoryClient.delete(id).getBody();
	}
}

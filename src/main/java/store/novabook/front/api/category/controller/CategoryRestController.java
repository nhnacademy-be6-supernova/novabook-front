package store.novabook.front.api.category.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.category.dto.response.DeleteResponse;
import store.novabook.front.api.category.service.CategoryRestService;

@RestController
@RequestMapping("/api/v1/front/categories")
@RequiredArgsConstructor
public class CategoryRestController {
	private final CategoryRestService categoryRestService;

	@CrossOrigin(origins = "https://novabook.store")
	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteResponse> isRegistered(@PathVariable Long id) {
		DeleteResponse isRegistered = categoryRestService.delete(id);
		return ResponseEntity.ok().body(isRegistered);
	}
}

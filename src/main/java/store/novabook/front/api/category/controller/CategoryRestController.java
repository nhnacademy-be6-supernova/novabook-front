package store.novabook.front.api.category.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.category.service.CategoryRestService;

@RestController
@RequestMapping("/api/v1/store/categories")
@RequiredArgsConstructor
public class CategoryRestController {
	private final CategoryRestService categoryRestService;

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(409).build();
		}
		categoryRestService.deleteCategory(id);
		return ResponseEntity.ok().build();
	}
}

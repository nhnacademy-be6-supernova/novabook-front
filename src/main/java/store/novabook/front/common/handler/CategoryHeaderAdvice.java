package store.novabook.front.common.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.category.service.CategoryService;

@ControllerAdvice
@RequiredArgsConstructor
public class CategoryHeaderAdvice {

	private final CategoryService categoryService;

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("categories", categoryService.getCategoryAll());
	}

}

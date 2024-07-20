package store.novabook.front.store.store;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import store.novabook.front.api.book.dto.response.GetBookToMainResponseMap;
import store.novabook.front.api.book.service.BookService;

@Controller
@AllArgsConstructor
public class StoreIndexController {
	private final BookService bookService;

	@GetMapping
	public String index(Model model) {
		GetBookToMainResponseMap responseMap = bookService.getBookToMainPage();
		model.addAttribute("newBooks", responseMap.mainBookData().get("newBooks"));
		model.addAttribute("popularBooks", responseMap.mainBookData().get("popularBooks"));
		return "store/store_index";
	}
}
package store.novabook.front.store.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.service.BookService;

@RequestMapping("/search")
@Controller
@RequiredArgsConstructor
public class BookSearchController {
	private final BookService bookService;

	@GetMapping("/title")
	public String searchTitle(Model model) {
		return "store/book/book_list";
	}
}

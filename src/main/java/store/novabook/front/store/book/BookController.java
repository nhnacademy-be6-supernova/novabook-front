package store.novabook.front.store.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.service.BookServiceImpl;

@RequestMapping("/books")

@Controller
@RequiredArgsConstructor
public class BookController {

	private final BookServiceImpl bookServiceImpl;

	@GetMapping
	public String getBookAll() {
		return "store/book/book_list";
	}

	@GetMapping("/book/{bookId}")
	public String getBook(@PathVariable Long bookId, Model model) {
		model.addAttribute("book", bookServiceImpl.getBookClient(bookId));
		return "store/book/book_detail";
	}
}

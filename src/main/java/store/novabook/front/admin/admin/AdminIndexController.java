package store.novabook.front.admin.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.service.impl.BookServiceImpl;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminIndexController {

	private final BookServiceImpl bookServiceImpl;
	private static final String DEFAULT_PAGE_SIZE = "10";
	private static final String DEFAULT_PAGE_NUM = "0";

	@GetMapping
	public String index() {
		return "admin/admin_index";
	}

	@GetMapping("/book/form")
	public String getBookForm() {
		return "admin/book/book_form";
	}

	@GetMapping("/books")
	public String getBookAll(Model model,
		@RequestParam(defaultValue = DEFAULT_PAGE_NUM) int page,
		@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size) {
		model.addAttribute("books", bookServiceImpl.getBookAll(page, size));
		return "admin/book/book_list";
	}

}

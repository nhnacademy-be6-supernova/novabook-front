package store.novabook.front.admin.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.dto.GetBookAllResponse;
import store.novabook.front.api.book.service.BookService;
import store.novabook.front.common.response.PageResponse;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminIndexController {

	private final BookService bookService;
	private static final String DEFAULT_PAGE_SIZE = "10";

	@GetMapping
	public String index() {
		return "admin/admin_index";
	}

	@GetMapping("book/form")
	public String getBookForm() {
		return "admin/book/book_form";
	}

	@PostMapping
	public String createBook() {
		return "";
	}

	@GetMapping("books")
	public String getBookAll(Model model,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size) {

		PageResponse<GetBookAllResponse> bookAll = bookService.getBookAll(page - 1, size);

		model.addAttribute("books", bookAll);
		return "/admin/book/book_list";
	}


}

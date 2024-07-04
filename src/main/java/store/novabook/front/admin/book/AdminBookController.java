package store.novabook.front.admin.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.dto.request.CreateBookRequest;

import store.novabook.front.api.book.dto.request.UpdateBookRequest;
import store.novabook.front.api.book.dto.response.GetBookAllResponse;
import store.novabook.front.api.book.service.BookService;
import store.novabook.front.api.category.service.CategoryService;
import store.novabook.front.api.tag.service.TagService;
import store.novabook.front.common.response.PageResponse;

@RequestMapping("/admin/books")
@Controller
@RequiredArgsConstructor
public class AdminBookController {

	private final BookService bookService;
	private final CategoryService categoryService;
	private final TagService tagService;
	private static final String DEFAULT_PAGE_SIZE = "10";
	private static final String DEFAULT_PAGE_NUM = "0";

	@GetMapping
	public String getBookAll(Model model,
		@RequestParam(defaultValue = DEFAULT_PAGE_NUM) int page,
		@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size) {

		PageResponse<GetBookAllResponse> bookAll = bookService.getBookAll(page, size);

		model.addAttribute("books", bookAll);
		return "admin/book/book_list";
	}

	@GetMapping("/form")
	public String getBookForm(Model model) {
		model.addAttribute("categories", categoryService.getCategoryAll());
		model.addAttribute("tags", tagService.getTagList());
		return "admin/book/book_form";
	}

	@CrossOrigin(origins = "https://novabook.store")
	// @CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("/book/form")
	public String createBook(@RequestBody CreateBookRequest createBookRequest) {
		bookService.createBook(createBookRequest);
		return "redirect:/admin/books";
	}

	@CrossOrigin(origins = "https://novabook.store")
	// @CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("/book/update")
	public String updateBook(@RequestBody UpdateBookRequest updateBookRequest) {
		bookService.updateBook(updateBookRequest);
		return "redirect:/admin/books";
	}

}

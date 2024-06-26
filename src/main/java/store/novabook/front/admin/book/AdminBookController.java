package store.novabook.front.admin.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.dto.request.CreateBookRequest;
import store.novabook.front.api.book.dto.request.UpdateBookRequest;
import store.novabook.front.api.book.service.BookService;
import store.novabook.front.api.category.service.CategoryService;
import store.novabook.front.api.tag.service.TagService;

@RequestMapping("/admin/books")
@Controller
@RequiredArgsConstructor
public class AdminBookController {

	private final BookService bookService;
	private final CategoryService categoryService;
	private final TagService tagService;

	@GetMapping("/book/form")
	public String getBookForm(Model model) {
		model.addAttribute("categories", categoryService.getCategoryAll());
		model.addAttribute("tags", tagService.getTagList());
		return "admin/book/book_form";
	}


	@CrossOrigin(origins = "https://novabook.store")
	@PostMapping("/book/form")
	public String createBook(@RequestBody CreateBookRequest createBookRequest) {
		bookService.createBook(createBookRequest);
		return "redirect:/admin/books";
	}

	@CrossOrigin(origins = "https://novabook.store")
	@PostMapping("/book/update")
	public String updateBook(@RequestBody UpdateBookRequest updateBookRequest) {
		bookService.updateBook(updateBookRequest);
		return "redirect:/admin/books";
	}

}

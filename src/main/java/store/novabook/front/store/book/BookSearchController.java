package store.novabook.front.store.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.dto.response.GetBookSearchResponse;
import store.novabook.front.api.book.service.BookService;
import store.novabook.front.common.response.PageResponse;

@RequestMapping("/search")
@Controller
@RequiredArgsConstructor
public class BookSearchController {
	private final BookService bookService;
	public static final String DEFAULT_PAGE = "0";
	private static final String DEFAULT_SIZE = "20";
	private static final String DEFAULT_SORT = "createdAt";

	@GetMapping("/title")
	public String searchTitle(Model model, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
		@RequestParam(defaultValue = DEFAULT_SIZE) int size, @RequestParam(defaultValue = DEFAULT_SORT) String sort) {
		PageResponse<GetBookSearchResponse> bookSearches = bookService.getBookSearchAllPage(page, size, sort);
		model.addAttribute("bookSearches", bookSearches);
		return "store/book/book_list";
	}
}

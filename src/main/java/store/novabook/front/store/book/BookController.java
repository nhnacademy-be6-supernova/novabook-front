package store.novabook.front.store.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.dto.response.GetReviewListResponse;
import store.novabook.front.api.book.likes.dto.LikeBookResponse;
import store.novabook.front.api.book.likes.service.LikeBookRestService;
import store.novabook.front.api.book.service.BookService;
import store.novabook.front.api.book.service.ReviewService;
import store.novabook.front.common.exception.FeignClientException;
import store.novabook.front.common.security.aop.CurrentMembers;


@RequestMapping("/books")

@Controller
@RequiredArgsConstructor
public class BookController {
	private final ReviewService reviewService;
	private final BookService bookService;
	private final LikeBookRestService likeBookRestService;

	@GetMapping
	public String getBookAll() {
		return "store/book/book_list";
	}

	@GetMapping("/book/{bookId}")
	public String getBook(@PathVariable Long bookId, @CurrentMembers(required = false) Long memberId, Model model) {
		try {
			model.addAttribute("book", bookService.getBookClient(bookId));
			GetReviewListResponse response = reviewService.getReviewsByBookId(bookId);
			model.addAttribute("reviews", response);

			if (memberId != null) {
				LikeBookResponse likeBookResponse = likeBookRestService.getBookLikes(bookId);
				model.addAttribute("isLiked", likeBookResponse.isLiked());
				return "store/book/book_detail";
			}
			model.addAttribute("isLiked", false);
		} catch (FeignClientException e) {
			return "error/404";
		}
		return "store/book/book_detail";
	}
}

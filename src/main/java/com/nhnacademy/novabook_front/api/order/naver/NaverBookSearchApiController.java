package com.nhnacademy.novabook_front.api.order.naver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("admin/books/book/search")
@RestController
public class NaverBookSearchApiController {

	private final BookSearchService bookSearchService;

	public NaverBookSearchApiController(BookSearchService bookSearchService) {
		this.bookSearchService = bookSearchService;
	}

	@GetMapping
	public String getBooks(@RequestParam String query) {
		return bookSearchService.searchBooks(query);
	}

}
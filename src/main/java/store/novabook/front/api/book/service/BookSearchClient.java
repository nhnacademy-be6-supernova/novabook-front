package store.novabook.front.api.book.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.book.dto.response.GetBookSearchResponse;
import store.novabook.front.common.response.PageResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/search", contextId = "bookSearchClient")
public interface BookSearchClient {
	@GetMapping("/keyword")
	PageResponse<GetBookSearchResponse> searchByKeyword(@RequestParam String title, @RequestParam int page,
		@RequestParam int size, @RequestParam(required = false) String sort);

	@GetMapping("/author")
	PageResponse<GetBookSearchResponse> searchByAuthor(@RequestParam String author, @RequestParam int page,
		@RequestParam int size, @RequestParam(required = false) String sort);

	@GetMapping("/publish")
	PageResponse<GetBookSearchResponse> searchByPublish(@RequestParam String publish, @RequestParam int page,
		@RequestParam int size, @RequestParam(required = false) String sort);

	@GetMapping("/category")
	PageResponse<GetBookSearchResponse> searchByCategory(@RequestParam String category, @RequestParam int page,
		@RequestParam int size, @RequestParam(required = false) String sort);
}

package store.novabook.front.api.book;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import store.novabook.front.api.ApiResponse;
import store.novabook.front.api.book.dto.GetBookResponse;

@FeignClient(name = "storeBookClient", url = "http://localhost:8090/books")
public interface BookClient {
	// @GetMapping
	// ResponseEntity<Page<GetBookAllResponse>> getBooks(Pageable pageable);



	@GetMapping("/book/{id}")
	ApiResponse<GetBookResponse> getBook(@PathVariable("id") Long memberId);
}

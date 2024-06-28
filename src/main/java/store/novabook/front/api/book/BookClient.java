package store.novabook.front.api.book;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.book.dto.CreateBookRequest;
import store.novabook.front.api.book.dto.GetBookAllResponse;
import store.novabook.front.api.book.dto.GetBookResponse;
import store.novabook.front.api.book.dto.UpdateBookRequest;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

@FeignClient(name = "storeBookClient")
public interface BookClient {

	@GetMapping("/{id}")
	ApiResponse<GetBookResponse> getBook(@PathVariable("id") Long id);

	@GetMapping
	PageResponse<GetBookAllResponse> getBookAll(@RequestParam int page, @RequestParam int size);

	@PostMapping
	ResponseEntity<Void> createBook(@RequestBody CreateBookRequest createBookRequest);

	@PutMapping
	ResponseEntity<Void> updateBook(@RequestBody UpdateBookRequest updateBookRequest);

}

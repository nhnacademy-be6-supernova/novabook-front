package store.novabook.front.api.book;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.ApiResponse;
import store.novabook.front.api.PageResponse;
import store.novabook.front.api.book.dto.CreateBookRequest;
import store.novabook.front.api.book.dto.GetBookAllResponse;
import store.novabook.front.api.book.dto.GetBookResponse;

@FeignClient(name = "storeBookClient", url = "http://localhost:8090/api/v1/store/books")
public interface BookClient {

	@GetMapping("/book/{id}")
	ApiResponse<GetBookResponse> getBook(@PathVariable("id") Long id);

	@GetMapping
	PageResponse<GetBookAllResponse> getBookAll(@RequestParam int page, @RequestParam int size);

	@PostMapping
	ResponseEntity<Void> createBook(@RequestBody CreateBookRequest createBookRequest);

}

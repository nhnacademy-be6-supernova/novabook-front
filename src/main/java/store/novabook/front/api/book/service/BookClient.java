package store.novabook.front.api.book.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.book.dto.request.CreateBookRequest;
import store.novabook.front.api.book.dto.request.UpdateBookRequest;
import store.novabook.front.api.book.dto.response.GetBookAllResponse;
import store.novabook.front.api.book.dto.response.GetBookResponse;
import store.novabook.front.api.book.dto.response.GetBookToMainResponseMap;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/books", contextId = "BookClient")
public interface BookClient {

	@GetMapping("/{id}")
	ApiResponse<GetBookResponse> getBook(@PathVariable("id") Long id);

	@GetMapping
	PageResponse<GetBookAllResponse> getBookAll(@RequestParam int page, @RequestParam int size);

	@PostMapping
	ResponseEntity<Void> createBook(@RequestBody CreateBookRequest createBookRequest);

	@PutMapping
	ResponseEntity<Void> updateBook(@RequestBody UpdateBookRequest updateBookRequest);

	@GetMapping("/main")
	ApiResponse<GetBookToMainResponseMap> getBookToMainPage();
}

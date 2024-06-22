package com.nhnacademy.novabook_front.api.book;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nhnacademy.novabook_front.api.ApiResponse;
import com.nhnacademy.novabook_front.api.book.dto.GetBookResponse;

@FeignClient(name = "storeBookClient", url = "http://localhost:8090/books")
public interface BookClient {
	// @GetMapping
	// ResponseEntity<Page<GetBookAllResponse>> getBooks(Pageable pageable);

	@GetMapping("/book/{id}")
	ApiResponse<GetBookResponse> getBook(@PathVariable("id") Long id);
}

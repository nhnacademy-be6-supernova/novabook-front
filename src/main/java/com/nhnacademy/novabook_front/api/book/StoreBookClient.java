package com.nhnacademy.novabook_front.api.book;

import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "storeBookClient", url = "https://localhost:8080/books")
public interface StoreBookClient {
	// @GetMapping
	// ResponseEntity<Page<GetBookAllResponse>> getBooks(Pageable pageable);

}

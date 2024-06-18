package com.nhnacademy.novabook_front.api.naver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverSearchClient", url = "https://openapi.naver.com/v1/search/book.json")
public interface NaverBookSearchApiClient {

	@GetMapping
	String getSearch(
		@RequestHeader("X-Naver-Client-Id") String clientId,
		@RequestHeader("X-Naver-Client-Secret") String clientSecret,
		@RequestParam("query") String query,
		@RequestParam(value = "display", required = false, defaultValue = "10") int display,
		@RequestParam(value = "start", required = false, defaultValue = "1") int start
	);
}

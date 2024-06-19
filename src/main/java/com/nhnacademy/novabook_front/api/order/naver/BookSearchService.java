package com.nhnacademy.novabook_front.api.order.naver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BookSearchService {

	@Value("${naver.book.clientkey}")
	private String clientId;
	@Value("${naver.book.secretkey}")
	private String clientSecret;

	private final NaverBookSearchApiClient naverBookSearchApiClient;

	@Autowired
	public BookSearchService(NaverBookSearchApiClient naverBookSearchApiClient) {
		this.naverBookSearchApiClient = naverBookSearchApiClient;
	}

	public String searchBooks(String query) {
		return naverBookSearchApiClient.getSearch(clientId, clientSecret, query, 5, 1);
	}
}

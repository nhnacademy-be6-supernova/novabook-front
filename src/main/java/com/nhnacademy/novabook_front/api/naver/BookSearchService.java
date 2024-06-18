package com.nhnacademy.novabook_front.api.naver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookSearchService {

	private final NaverBookSearchApiClient naverBookSearchApiClient;

	@Autowired
	public BookSearchService(NaverBookSearchApiClient naverBookSearchApiClient) {
		this.naverBookSearchApiClient = naverBookSearchApiClient;
	}

	public String searchBooks(String query) {
		String clientId = "aY3htNCRURon01pEVu8z";
		String clientSecret = "DWSGBOBel9";
		return naverBookSearchApiClient.getSearch(clientId, clientSecret, query, 5, 1);
	}
}

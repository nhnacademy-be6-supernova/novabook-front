package com.nhnacademy.novabook_front.api.book.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nhnacademy.novabook_front.api.ApiResponse;
import com.nhnacademy.novabook_front.api.book.BookClient;
import com.nhnacademy.novabook_front.api.book.dto.GetBookResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookClient bookClient;

	public GetBookResponse getBookClient(Long id) {
		ApiResponse<GetBookResponse> book = bookClient.getBook(id);

		GetBookResponse getBookResponse = book.getBody();
		return getBookResponse;
	}
}

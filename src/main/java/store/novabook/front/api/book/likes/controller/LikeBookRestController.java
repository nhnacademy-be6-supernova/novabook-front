package store.novabook.front.api.book.likes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.likes.dto.LikeBookResponse;
import store.novabook.front.api.book.likes.service.LikeBookRestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/front/books/likes")
public class LikeBookRestController {

	private final LikeBookRestService likeBookRestService;

	@PostMapping("/{bookId}")
	public ResponseEntity<LikeBookResponse> isLiked(@PathVariable Long bookId) {
		LikeBookResponse likeBookResponse = likeBookRestService.likeButton(bookId);
		return ResponseEntity.ok().body(likeBookResponse);
	}
}

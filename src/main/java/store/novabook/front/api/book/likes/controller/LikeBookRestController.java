package store.novabook.front.api.book.likes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.book.likes.dto.LikeBookRequest;
import store.novabook.front.api.book.likes.dto.LikeBookResponse;
import store.novabook.front.api.book.likes.service.LikeBookRestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/front/books/likes")
public class LikeBookRestController {

	private final LikeBookRestService likeBookRestService;

	@PostMapping
	public ResponseEntity<LikeBookResponse> isLiked(@Valid @RequestBody LikeBookRequest request) {
		LikeBookResponse isPressedLiked = likeBookRestService.createLikes(request);
		return ResponseEntity.ok().body(isPressedLiked);
	}
}

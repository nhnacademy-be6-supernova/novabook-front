package store.novabook.front.api.book.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import store.novabook.front.api.book.dto.response.GetBookLikeResponse;
import store.novabook.front.api.book.likes.dto.LikeBookRequest;
import store.novabook.front.api.book.likes.dto.LikeBookResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

@FeignClient(name = "bookLikeClient")
public interface BookLikeClient {

	@GetMapping("/member")
	PageResponse<GetBookLikeResponse> getBookLikeAllPage(@RequestParam int page, @RequestParam int size);

	@DeleteMapping("/{likesId}")
	ApiResponse<Void> deleteBookLike(@PathVariable("likesId") Long likesId);

	@PostMapping
	ApiResponse<LikeBookResponse> createLikes(@RequestBody LikeBookRequest request);

}

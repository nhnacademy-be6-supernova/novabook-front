package store.novabook.front.api.book.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.book.dto.response.GetBookLikeResponse;
import store.novabook.front.api.book.likes.dto.LikeBookResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

@FeignClient(name = "bookLikeClient")
public interface BookLikeClient {

	@GetMapping("/member")
	PageResponse<GetBookLikeResponse> getBookLikeAllPage(@RequestParam int page, @RequestParam int size);

	@PostMapping("/{bookId}")
	ApiResponse<LikeBookResponse> buttonLikes(@PathVariable Long bookId);

	@GetMapping("/{bookId}")
	ApiResponse<LikeBookResponse> getBookLike(@PathVariable("bookId") Long bookId);

}

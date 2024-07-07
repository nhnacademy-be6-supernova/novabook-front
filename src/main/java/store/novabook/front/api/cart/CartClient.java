package store.novabook.front.api.cart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.cart.dto.request.CreateCartBookListRequest;
import store.novabook.front.api.cart.dto.request.CreateCartBookRequest;
import store.novabook.front.api.cart.dto.request.DeleteCartBookListRequest;
import store.novabook.front.api.cart.dto.request.UpdateCartBookQuantityRequest;
import store.novabook.front.api.cart.dto.response.CartIdResponse;
import store.novabook.front.api.cart.dto.response.CreateCartBookListResponse;
import store.novabook.front.api.cart.dto.response.CreateCartBookResponse;
import store.novabook.front.api.cart.dto.response.GetCartResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "cartClient")
public interface CartClient {

	@GetMapping("/member")
	ApiResponse<GetCartResponse> getCartBookAllByMemberId();

	@PostMapping("/create")
	ApiResponse<CartIdResponse> createCartIdByMemberId();

	@PostMapping("/add")
	ApiResponse<CreateCartBookResponse> addCartBook(@RequestBody CreateCartBookRequest request);

	@PostMapping("/adds")
	ApiResponse<CreateCartBookListResponse> addCartBooks(@RequestBody CreateCartBookListRequest request);

	@PutMapping("/update")
	ApiResponse<Void> updateCartBook(@RequestBody UpdateCartBookQuantityRequest request);

	@DeleteMapping("/{bookId}")
	ApiResponse<Void> deleteCartBook(@PathVariable Long bookId);

	@DeleteMapping
	ApiResponse<Void> deleteCartBooks(@RequestBody DeleteCartBookListRequest deleteCartBookListRequest);
}

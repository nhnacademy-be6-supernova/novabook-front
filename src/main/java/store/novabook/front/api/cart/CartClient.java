package store.novabook.front.api.cart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.cart.dto.response.CartIdResponse;
import store.novabook.front.api.cart.dto.request.CreateCartBookRequest;
import store.novabook.front.api.cart.dto.response.CreateCartBookResponse;
import store.novabook.front.api.cart.dto.response.GetCartResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "cartClient")
public interface CartClient {

	@GetMapping
	ApiResponse<CartIdResponse> getCartIdByMemberId();

	@PostMapping("/create")
	ApiResponse<CartIdResponse> createCartIdByMemberId();

	@GetMapping("/{cartId}")
	ApiResponse<GetCartResponse> getCartListAll(@PathVariable Long cartId);

	@GetMapping("/member")
	ApiResponse<GetCartResponse> getCartBookAllByMemberId();

	@PostMapping("/add")
	ApiResponse<CreateCartBookResponse> createCartBook(@RequestBody CreateCartBookRequest request);

	@DeleteMapping("/{cartId}")
	ApiResponse<Void> deleteCartBook(@PathVariable Long cartId);
}

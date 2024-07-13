package store.novabook.front.api.cart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.cart.dto.CartBookDTO;
import store.novabook.front.api.cart.dto.CartBookIdDTO;
import store.novabook.front.api.cart.dto.CartBookListDTO;
import store.novabook.front.api.cart.dto.request.DeleteCartBookListRequest;
import store.novabook.front.api.cart.dto.request.UpdateCartBookQuantityRequest;
import store.novabook.front.api.cart.dto.response.CreateCartBookListResponse;
import store.novabook.front.api.cart.dto.response.CreateCartBookResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/carts", contextId = "cartClient")
public interface CartClient {

	@GetMapping("/member")
	ApiResponse<CartBookListDTO> getCartBookAllByMemberId();

	@PostMapping("/guest")
	ApiResponse<CartBookListDTO> getCartBookAllByGuest(@RequestBody CartBookIdDTO request);

	@PostMapping("/add")
	ApiResponse<CreateCartBookResponse> addCartBook(@RequestBody CartBookDTO request);

	@PostMapping("/adds")
	ApiResponse<CreateCartBookListResponse> addCartBooks(@RequestBody CartBookListDTO request);

	@PutMapping("/update")
	ApiResponse<Void> updateCartBook(@RequestBody UpdateCartBookQuantityRequest request);

	@DeleteMapping("/{bookId}")
	ApiResponse<Void> deleteCartBook(@PathVariable Long bookId);

	@DeleteMapping
	ApiResponse<Void> deleteCartBooks(@RequestBody DeleteCartBookListRequest deleteCartBookListRequest);
}

package store.novabook.front.store.cart.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.cart.CartClient;
import store.novabook.front.api.cart.dto.CartBookDTO;
import store.novabook.front.api.cart.dto.CartBookIdDTO;
import store.novabook.front.api.cart.dto.request.DeleteCartBookListRequest;
import store.novabook.front.api.cart.dto.request.UpdateCartBookQuantityRequest;
import store.novabook.front.api.cart.dto.response.CreateCartBookListResponse;
import store.novabook.front.api.cart.dto.response.CreateCartBookResponse;
import store.novabook.front.api.cart.dto.CartBookListDTO;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartClient cartClient;

	public CreateCartBookResponse addCartBook(CartBookDTO request) {
		return cartClient.addCartBook(request).getBody();
	}

	public CreateCartBookListResponse addCartBooks(CartBookListDTO request) {
		return cartClient.addCartBooks(request).getBody();
	}

	public CartBookListDTO getCartList() {
		return cartClient.getCartBookAllByMemberId().getBody();
	}

	public void deleteCartBook(Long bookId) {
		cartClient.deleteCartBook(bookId);
	}

	public void deleteCartBooks(DeleteCartBookListRequest request) {
		cartClient.deleteCartBooks(request);
	}

	public void updateCartBookQuantity(UpdateCartBookQuantityRequest request) {
		cartClient.updateCartBook(request);
	}

	public CartBookListDTO getCartListByGuest(CartBookIdDTO cartBookIdDTO) {
		return cartClient.getCartBookAllByGuest(cartBookIdDTO).getBody();
	}
}

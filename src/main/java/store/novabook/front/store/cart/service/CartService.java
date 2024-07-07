package store.novabook.front.store.cart.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.cart.CartClient;
import store.novabook.front.api.cart.dto.request.CreateCartBookListRequest;
import store.novabook.front.api.cart.dto.request.CreateCartBookRequest;
import store.novabook.front.api.cart.dto.request.DeleteCartBookListRequest;
import store.novabook.front.api.cart.dto.request.UpdateCartBookQuantityRequest;
import store.novabook.front.api.cart.dto.response.CreateCartBookListResponse;
import store.novabook.front.api.cart.dto.response.CreateCartBookResponse;
import store.novabook.front.api.cart.dto.response.GetCartResponse;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartClient cartClient;

	public CreateCartBookResponse addCartBook(CreateCartBookRequest request) {
		return cartClient.addCartBook(request).getBody();
	}

	public CreateCartBookListResponse addCartBooks(CreateCartBookListRequest request) {
		return cartClient.addCartBooks(request).getBody();
	}

	public GetCartResponse getCartList() {
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
}

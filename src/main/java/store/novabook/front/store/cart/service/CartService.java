package store.novabook.front.store.cart.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.cart.CartClient;
import store.novabook.front.api.cart.dto.response.CartIdResponse;
import store.novabook.front.api.cart.dto.response.GetCartResponse;
import store.novabook.front.store.cart.repository.RedisCartRepository;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartClient cartClient;
	private final RedisCartRepository redisCartRepository;

	public GetCartResponse getCartList(Long cartId) {
		return cartClient.getCartListAll(cartId).getBody();
	}

	public GetCartResponse getCartListByMemberId() {
		return cartClient.getCartBookAllByMemberId().getBody();
	}

	public CartIdResponse createCartId() {
		return cartClient.createCartIdByMemberId().getBody();
	}

	public CartIdResponse getCartId() {
		return cartClient.getCartIdByMemberId().getBody();
	}
}

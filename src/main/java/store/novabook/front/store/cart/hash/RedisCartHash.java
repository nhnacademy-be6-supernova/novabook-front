package store.novabook.front.store.cart.hash;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Getter;
import store.novabook.front.api.cart.dto.CartBookDTO;
import store.novabook.front.api.cart.dto.CartBookListDTO;

@RedisHash(value = "cart", timeToLive = 7200)
@Getter
public class RedisCartHash {

	@Id
	Object cartId;

	List<CartBookDTO> cartBookList;

	@Builder
	public RedisCartHash(Object cartId, List<CartBookDTO> cartBookList) {
		this.cartId = cartId;
		this.cartBookList = cartBookList;
	}

	public static RedisCartHash of(Object cartId) {
		return RedisCartHash.builder()
			.cartId(cartId)
			.cartBookList(new ArrayList<>())
			.build();
	}

	public static RedisCartHash of(Object cartId, CartBookDTO request) {
		List<CartBookDTO> cartBookList = new ArrayList<>();
		cartBookList.add(request);
		return RedisCartHash.builder()
			.cartId(cartId)
			.cartBookList(cartBookList)
			.build();
	}

	public static RedisCartHash of(Object cartId, CartBookListDTO request) {
		return RedisCartHash.builder()
			.cartId(cartId)
			.cartBookList(new ArrayList<>(request.getCartBookList()))
			.build();
	}

	public void update(List<CartBookDTO> newCartBookList) {
		this.cartBookList = newCartBookList;
	}
}

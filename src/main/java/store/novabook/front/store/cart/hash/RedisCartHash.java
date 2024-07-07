package store.novabook.front.store.cart.hash;

import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Getter;
import store.novabook.front.api.cart.dto.request.CreateCartBookListRequest;
import store.novabook.front.api.cart.dto.request.CreateCartBookRequest;

@RedisHash("cart")
@Getter
public class RedisCartHash{

	@Id
	Object cartId;

	List<CreateCartBookRequest> cartBookList;


	@Builder
	public RedisCartHash(Object cartId, List<CreateCartBookRequest> cartBookList) {
		this.cartId = cartId;
		this.cartBookList = cartBookList;
	}
	public static RedisCartHash of(Object cartId) {
		return RedisCartHash.builder()
			.cartId(cartId)
			.cartBookList(Collections.emptyList())
			.build();
	}

	public static RedisCartHash of(Object cartId, CreateCartBookRequest request) {
		return RedisCartHash.builder()
			.cartId(cartId)
			.cartBookList(List.of(request))
			.build();
	}

	public static RedisCartHash of(Object cartId, CreateCartBookListRequest request) {
		return RedisCartHash.builder()
			.cartId(cartId)
			.cartBookList(request.cartBookList())
			.build();

	}

	public void update(List<CreateCartBookRequest> newCartBookList){
		this.cartBookList = newCartBookList;
	}

}
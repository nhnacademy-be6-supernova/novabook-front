package store.novabook.front.api.cart;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CartBook {
	private Long id;

	private Long cartId;

	private Long bookId;

	private int quantity;

	private boolean isExposed;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}

package store.novabook.front.api.cart.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record DeleteCartBookListRequest(
	@NotNull(message = "bookIds값이 null 입니다.")
	List<Long> bookIds) {
}

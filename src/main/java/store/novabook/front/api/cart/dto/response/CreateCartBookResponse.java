package store.novabook.front.api.cart.dto.response;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record CreateCartBookResponse(
	@NotNull(message = "ids값이 null입니다.")
	List<Long> ids) {
}

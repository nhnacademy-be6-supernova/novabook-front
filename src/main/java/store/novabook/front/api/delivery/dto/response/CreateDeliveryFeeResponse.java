package store.novabook.front.api.delivery.dto.response;

import jakarta.validation.constraints.NotNull;

public record CreateDeliveryFeeResponse(
	@NotNull(message = "reviewId 값을 입력해주세요.")
	Long id
) {
}

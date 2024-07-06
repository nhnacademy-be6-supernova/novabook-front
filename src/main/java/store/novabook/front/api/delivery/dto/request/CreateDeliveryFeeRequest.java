package store.novabook.front.api.delivery.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateDeliveryFeeRequest(
	@NotNull(message = "요금은 필수 값입니다.")
	Long fee
) {
}

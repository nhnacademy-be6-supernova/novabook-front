package store.novabook.front.api.order.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TossPaymentRequest(
	@NotNull String orderId,
	@Positive long amount,
	@NotNull String paymentKey
) {}

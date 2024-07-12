package store.novabook.front.api.order.dto.request;

import java.io.Serializable;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import store.novabook.front.api.order.dto.PaymentType;

public record PaymentRequest(
	PaymentType type,
	Long memberId,
	@NotNull
	UUID orderId,
	Object paymentInfo
) implements Serializable {}

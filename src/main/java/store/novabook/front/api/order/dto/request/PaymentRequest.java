package store.novabook.front.api.order.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import store.novabook.front.api.order.dto.PaymentType;

public record PaymentRequest(
	PaymentType type,
	Long memberId,
	@NotNull
	String orderCode,
	Object paymentInfo
) implements Serializable {}

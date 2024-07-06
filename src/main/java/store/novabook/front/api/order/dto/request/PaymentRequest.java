package store.novabook.front.api.order.dto.request;

import store.novabook.front.api.order.dto.PaymentType;

public record PaymentRequest(
	PaymentType type,
	Object paymentInfo
) {}

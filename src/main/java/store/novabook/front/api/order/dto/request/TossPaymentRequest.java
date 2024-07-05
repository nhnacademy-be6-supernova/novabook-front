package store.novabook.front.api.order.dto.request;

public record TossPaymentRequest(String orderId, long amount, String paymentKey) {
}

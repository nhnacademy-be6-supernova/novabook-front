package store.novabook.front.store.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import store.novabook.front.api.order.dto.PaymentType;

@Builder
@Setter
@Getter
public class RequestPayCancelMessage {
	PaymentType paymentType;
	long totalAmount;
	String orderCode;
	Long couponId;
	Long usePointAmount;
	Long memberId;
	Long earnPointAmount;
	String paymentKey;
	String status;
}

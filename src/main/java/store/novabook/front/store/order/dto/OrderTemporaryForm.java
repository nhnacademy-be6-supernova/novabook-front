package store.novabook.front.store.order.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("OrderForm")
public record OrderTemporaryForm(
	@Id
	Long memberId,
	String senderName,
	String senderPhone
) {
}

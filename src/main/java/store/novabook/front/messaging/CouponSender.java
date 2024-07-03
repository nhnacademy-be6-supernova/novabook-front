package store.novabook.front.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.messaging.dto.CreateCouponMessage;

@Service
@RequiredArgsConstructor
public class CouponSender {

	private final RabbitTemplate rabbitTemplate;

	@Value("${rabbitmq.exchange.couponOperation}")
	private String couponOperationExchange;

	@Value("${rabbitmq.routing.couponCreateNormal}")
	private String couponCreateNormalRoutingKey;

	public void sendToNormalCouponCreateQueue(CreateCouponMessage message) {
		rabbitTemplate.convertAndSend(couponOperationExchange, couponCreateNormalRoutingKey, message);
	}
}

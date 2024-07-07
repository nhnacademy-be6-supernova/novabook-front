package store.novabook.front.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.messaging.dto.DownloadCouponMessageRequest;

@Service
@RequiredArgsConstructor
public class CouponSender {

	private final RabbitTemplate rabbitTemplate;

	@Value("${rabbitmq.exchange.couponOperation}")
	private String couponOperationExchange;

	@Value("${rabbitmq.routing.couponCreateHighTraffic}")
	private String couponCreateHighTrafficExchange;

	public void sendToHighTrafficCouponCreateQueue(DownloadCouponMessageRequest message) {
		rabbitTemplate.convertAndSend(couponOperationExchange, couponCreateHighTrafficExchange, message);
	}
}

package store.novabook.front.redis.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.notification.NotificationController;

@Service
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener {

	private final NotificationController notificationController;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String messageStr = new String(message.getBody());
		String[] parts = messageStr.split(":", 2);
		String clientId = parts[0];
		String notificationMessage = parts[1];

		notificationController.notifyClient(clientId, notificationMessage);
	}
}
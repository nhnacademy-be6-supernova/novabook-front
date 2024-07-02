package store.novabook.front.common.sse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class NotificationController {

	private final Map<String, SseEmitter> clients = new ConcurrentHashMap<>();

	@GetMapping("/notifications/subscribe")
	public SseEmitter subscribe(String clientId) {
		SseEmitter emitter = new SseEmitter();
		clients.put(clientId, emitter);
		emitter.onCompletion(() -> clients.remove(clientId));
		emitter.onTimeout(() -> clients.remove(clientId));
		return emitter;
	}

	public void notifyClient(String clientId, String message) {
		SseEmitter emitter = clients.get(clientId);
		if (emitter != null) {
			try {
				emitter.send(message);
				emitter.complete();
			} catch (IOException e) {
				clients.remove(clientId);
			}
		}
	}
}
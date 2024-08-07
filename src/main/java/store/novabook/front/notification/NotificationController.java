package store.novabook.front.notification;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NotificationController {

	private final Map<String, SseEmitter> clients = new ConcurrentHashMap<>();

	@GetMapping("/notifications/subscribe")
	public SseEmitter subscribe(@RequestParam String clientId) {
		SseEmitter emitter = new SseEmitter(600000L);
		clients.put(clientId, emitter);
		emitter.onCompletion(() -> clients.remove(clientId));
		emitter.onTimeout(() -> clients.remove(clientId));
		return emitter;
	}

	public void notifyClient(@RequestParam String clientId, @RequestParam("message") String message) {
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
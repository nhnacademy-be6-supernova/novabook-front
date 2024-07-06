package store.novabook.front.dooray;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoorayServiceImpl implements DoorayService {

	private final DoorayHookClient doorayHookClient;

	@Override
	public void sendAuthCode(String token, String authCode) {
		Map<String, Object> message = new HashMap<>();
		message.put("botname", "novabook Bot");
		message.put("text", "휴면 계정 해지를 위해 다음 인증 코드를 입력하세요." + authCode);
		doorayHookClient.sendMessage(token, message);
	}
}

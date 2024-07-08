package store.novabook.front.dooray;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.common.response.ApiResponse;

@Service
@RequiredArgsConstructor
public class DoorayServiceImpl implements DoorayService {

	private final DoorayHookClient doorayHookClient;

	@Override
	public void sendAuthCode(DoorayAuthRequest request) {
		Map<String, Object> message = Map.of("memberId", request.uuid());
		doorayHookClient.sendMessage(message);
	}

	@Override
	public boolean confirmAuthCode(DoorayAuthCodeRequest request) {
		Map<String, Object> message = Map.of("memberId", request.uuid(), "authCode", request.authCode());
		ApiResponse<String> response = doorayHookClient.confirmDormantMember(message);
		return (boolean)response.getHeader().get("isSuccessful");
	}
}

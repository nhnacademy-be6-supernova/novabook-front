package store.novabook.front.dooray;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoorayServiceImpl implements DoorayService {

	private final DoorayHookClient doorayHookClient;

	@Override
	public void sendAuthCode(DoorayAuthRequest request) {
		doorayHookClient.sendMessage(request);
	}

	@Override
	public void confirmAuthCode(DoorayAuthCodeRequest request) {
		doorayHookClient.confirmDormantMember(request);
	}
}

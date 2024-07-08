package store.novabook.front.api.member.dooray.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.dooray.dto.DoorayAuthCodeRequest;
import store.novabook.front.api.member.dooray.dto.DoorayAuthRequest;
import store.novabook.front.api.member.dooray.service.DoorayHookClient;
import store.novabook.front.api.member.dooray.service.DoorayService;

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

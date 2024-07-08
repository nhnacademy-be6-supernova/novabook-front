package store.novabook.front.api.member.dooray.service;

import store.novabook.front.api.member.dooray.dto.DoorayAuthCodeRequest;
import store.novabook.front.api.member.dooray.dto.DoorayAuthRequest;

public interface DoorayService {
	void sendAuthCode(DoorayAuthRequest request);

	void confirmAuthCode(DoorayAuthCodeRequest request);
}

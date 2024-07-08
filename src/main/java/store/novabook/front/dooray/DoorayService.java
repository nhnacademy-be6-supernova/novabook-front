package store.novabook.front.dooray;

public interface DoorayService {
	void sendAuthCode(DoorayAuthRequest request);

	void confirmAuthCode(DoorayAuthCodeRequest request);
}

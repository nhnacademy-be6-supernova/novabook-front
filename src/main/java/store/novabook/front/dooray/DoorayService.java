package store.novabook.front.dooray;

public interface DoorayService {
	void sendAuthCode(DoorayAuthRequest request);

	boolean confirmAuthCode(DoorayAuthCodeRequest request);
}

package store.novabook.front.api.member.member.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;

@Service
@RequiredArgsConstructor
public class TestService {

	private final TestClient testClient;

	public void test() {
		System.out.println("test");
	}

	public void test2() {
		GetNewTokenRequest getNewTokenRequest = new GetNewTokenRequest("1234");
		ResponseEntity<GetNewTokenResponse> getNewTokenResponseResponseEntity = testClient.newToken(
			getNewTokenRequest);
		System.out.println();
	}
}

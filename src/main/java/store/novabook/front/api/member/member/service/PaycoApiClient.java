package store.novabook.front.api.member.member.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "paycoApiClient", url = "https://id.payco.com/oauth2.0")
public interface PaycoApiClient {

	@PostMapping("/token")
	Map<String, Object> getAuthorizationToken(
		@RequestParam("grant_type") String grantType,
		@RequestParam("client_id") String clientId,
		@RequestParam("client_secret") String clientSecret,
		@RequestParam("code") String code,
		@RequestParam("redirect_uri") String redirectUri,
		@RequestParam("state") String state
	);

	@GetMapping("/logout")
	String logout(
		@RequestParam("client_id") String clientId,
		@RequestParam("client_secret") String clientSecret,
		@RequestParam("token") String token
	);
}

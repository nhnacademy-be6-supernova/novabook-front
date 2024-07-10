package store.novabook.front.api.member.member.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.common.security.aop.GetMembersTokenResponse;

@FeignClient(name = "testClient", url = "http://localhost:9777/auth")
public interface TestClient {
	@PostMapping("/refresh")
	ResponseEntity<GetNewTokenResponse> newToken(@RequestBody GetNewTokenRequest getNewTokenRequest);
}

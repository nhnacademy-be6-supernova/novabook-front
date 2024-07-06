package store.novabook.front.api.member.member.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "paycoLoginCleint", url = "https://apis-payco.krp.toastoven.net")
public interface PaycoLoginClient {

	@PostMapping("/payco/friends/find_member_v2.json")
	String login(@RequestHeader("client_id") String clientId,
		@RequestHeader("access_token") String accessToken);
}

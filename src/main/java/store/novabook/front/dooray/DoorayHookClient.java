package store.novabook.front.dooray;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "doorayHookClient")
public interface DoorayHookClient {

	@PostMapping("/sendAuthCode")
	ApiResponse<String> sendMessage(@RequestHeader("Authorization") String memberId, @RequestBody Map<String, Object> message);

	@PostMapping("/dooray/confirm")
	ApiResponse<String> confirmDormantMember(@RequestHeader("Authorization") String memberId, @RequestBody Map<String, String> request);

}

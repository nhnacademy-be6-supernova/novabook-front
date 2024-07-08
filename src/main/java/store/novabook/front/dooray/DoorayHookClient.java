package store.novabook.front.dooray;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "doorayHookClient")
public interface DoorayHookClient {

	@PostMapping("/sendAuthCode")
	ApiResponse<String> sendMessage(@RequestBody Map<String, Object> message);

	@PostMapping("/confirm")
	ApiResponse<String> confirmDormantMember(@RequestBody Map<String, Object> request);

}

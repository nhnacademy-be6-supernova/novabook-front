package store.novabook.front.dooray;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "doorayHookClient")
public interface DoorayHookClient {

	@PostMapping("/sendAuthCode")
	ApiResponse<Void> sendMessage(@RequestBody DoorayAuthRequest request);

	@PostMapping("/confirm")
	ApiResponse<Void> confirmDormantMember(@RequestBody DoorayAuthCodeRequest request);

}

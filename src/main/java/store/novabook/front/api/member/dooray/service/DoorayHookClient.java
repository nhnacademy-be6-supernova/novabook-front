package store.novabook.front.api.member.dooray.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.api.member.dooray.dto.DoorayAuthCodeRequest;
import store.novabook.front.api.member.dooray.dto.DoorayAuthRequest;

@FeignClient(name = "doorayHookClient")
public interface DoorayHookClient {

	@PostMapping("/send-auth-code")
	ApiResponse<Void> sendMessage(@RequestBody DoorayAuthRequest request);

	@PostMapping("/confirm")
	ApiResponse<Void> confirmDormantMember(@RequestBody DoorayAuthCodeRequest request);

}

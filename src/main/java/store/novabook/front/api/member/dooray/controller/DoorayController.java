package store.novabook.front.api.member.dooray.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.novabook.front.common.exception.FeignClientException;
import store.novabook.front.api.member.dooray.service.DoorayService;
import store.novabook.front.api.member.dooray.dto.DoorayAuthCodeRequest;
import store.novabook.front.api.member.dooray.dto.DoorayAuthRequest;
import store.novabook.front.api.member.dooray.dto.SendAuthResponse;

@RestController
@RequestMapping("/dooray")
@RequiredArgsConstructor
public class DoorayController {

	private final DoorayService doorayService;

	@PostMapping("/send-auth-code")
	public ResponseEntity<SendAuthResponse> sendAuthCode(@RequestBody DoorayAuthRequest request) {
		doorayService.sendAuthCode(request);
		return ResponseEntity.ok().body(new SendAuthResponse("인증코드가 발송되었습니다."));
	}

	@PostMapping("/confirm")
	public ResponseEntity<Object> confirm(@RequestBody DoorayAuthCodeRequest request) {
		try {
			doorayService.confirmAuthCode(request);
		} catch (FeignClientException e) {
			return ResponseEntity.status(e.getStatus()).body("유효하지 않은 코드입니다.");
		}

		return ResponseEntity.ok().body(new SendAuthResponse("해지가 성공적으로 완료되었습니다."));
	}
}

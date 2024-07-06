package store.novabook.front.dooray;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dooray")
@RequiredArgsConstructor
public class DoorayController {

	private final DoorayService doorayService;

	@PostMapping("/sendAuthCode")
	public ResponseEntity<String> sendAuthCode(@RequestHeader("Authorization") String token, @RequestHeader String authCode) {
		doorayService.sendAuthCode(token, authCode);
		return ResponseEntity.ok("인증번호가 발송되었습니다");

	}
}

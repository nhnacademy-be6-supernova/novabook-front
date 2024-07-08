package store.novabook.front.dooray;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dooray")
@RequiredArgsConstructor
public class DoorayController {

	private final DoorayService doorayService;

	@PostMapping("/sendAuthCode")
	public ResponseEntity<String> sendAuthCode(@RequestBody DoorayAuthRequest request) {
		doorayService.sendAuthCode(request);
		return ResponseEntity.ok("인증번호가 발송되었습니다");
	}

	@PostMapping("/confirm")
	public ResponseEntity<String> confirm(@RequestBody DoorayAuthCodeRequest request) {
		boolean result = doorayService.confirmAuthCode(request);
		if (result) {
			return ResponseEntity.ok("해지가 성공적으로 완료되었습니다.");
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 인증코드입니다.");
		}
	}
}

package store.novabook.front.dooray;

import java.util.Map;

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
	public ResponseEntity<String> sendAuthCode(@RequestBody Map<String, Object> body) {
		Long memberId = (Long) body.get("memberId");
		String authCode = (String) body.get("authCode");
		doorayService.sendAuthCode(memberId.toString(), authCode);
		return ResponseEntity.ok("인증번호가 발송되었습니다");
	}
}

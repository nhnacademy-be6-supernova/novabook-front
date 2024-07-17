package store.novabook.front.api.member.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.api.member.member.dto.request.DuplicateEmailRequest;
import store.novabook.front.api.member.member.dto.request.DuplicateLoginIdRequest;
import store.novabook.front.api.member.member.dto.response.DuplicateResponse;
import store.novabook.front.api.member.member.dto.response.MemberNameResponse;
import store.novabook.front.api.member.member.service.MemberRestService;
import store.novabook.front.common.security.aop.CurrentMembers;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/front/members")
public class MemberRestController {

	private final MemberRestService memberRestService;

	@CrossOrigin(origins = "https://novabook.store")
	@PostMapping("/login-id/is-duplicate")
	public ResponseEntity<DuplicateResponse> isCreatableLoginId(@RequestBody DuplicateLoginIdRequest request) {
		DuplicateResponse isDuplicateLoginId = memberRestService.isDuplicateLoginId(request);
		return ResponseEntity.ok().body(isDuplicateLoginId);
	}

	@CrossOrigin(origins = "https://novabook.store")
	@PostMapping("/email/is-duplicate")
	public ResponseEntity<DuplicateResponse> isCreatableEmail(@RequestBody DuplicateEmailRequest request) {
		DuplicateResponse isDuplicateEmail = memberRestService.isDuplicateEmail(request);
		return ResponseEntity.ok().body(isDuplicateEmail);
	}

	@GetMapping("/login-id/current")
	public ResponseEntity<Long> getCurrentLoginId(@CurrentMembers Long memberId) {
		if (memberId == null) {
			throw new IllegalArgumentException("현재 로그인 정보가 없습니다.");
		}
		return ResponseEntity.ok().body(memberId);
	}

	@GetMapping("/member-name")
	public ResponseEntity<MemberNameResponse> getMemberName() {
		MemberNameResponse response = memberRestService.getMemberName();
		return ResponseEntity.ok().body(response);
	}
}

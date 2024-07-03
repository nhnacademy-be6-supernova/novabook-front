package store.novabook.front.api.member.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.request.DuplicateEmailRequest;
import store.novabook.front.api.member.member.dto.response.DuplicateResponse;
import store.novabook.front.api.member.member.dto.request.DuplicateLoginIdRequest;
import store.novabook.front.api.member.member.service.MemberRestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store/members")
public class MemberRestController {

	private final MemberRestService memberRestService;

	@PostMapping("/loginId/is-duplicate")
	public ResponseEntity<DuplicateResponse> isCreatableLoginId(@RequestBody DuplicateLoginIdRequest request) {
		DuplicateResponse isDuplicateLoginId = memberRestService.isDuplicateLoginId(request);
		return ResponseEntity.ok().body(isDuplicateLoginId);
	}

	@PostMapping("/email/is-duplicate")
	public ResponseEntity<DuplicateResponse> isCreatableEmail(@RequestBody DuplicateEmailRequest request) {
		DuplicateResponse isDuplicateEmail = memberRestService.isDuplicateEmail(request);
		return ResponseEntity.ok().body(isDuplicateEmail);
	}

}

package store.novabook.front.api.member.address.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.address.dto.response.ExceedResponse;
import store.novabook.front.api.member.address.service.MemberAddressRestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store/addresses")
public class MemberAddressRestController {
	private final MemberAddressRestService memberAddressRestService;

	@GetMapping("/is-exceed")
	public ResponseEntity<ExceedResponse> isExceed() {
		ExceedResponse isExceedMemberAddressCount = memberAddressRestService.isExceedMemberAddressCount();
		return ResponseEntity.ok().body(isExceedMemberAddressCount);
	}

}

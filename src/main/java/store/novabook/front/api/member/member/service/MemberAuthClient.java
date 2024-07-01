package store.novabook.front.api.member.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.member.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.member.dto.LoginMemberResponse;

@FeignClient(name = "memberAuthClient")
public interface MemberAuthClient {
	@PostMapping("/login")
	ResponseEntity<LoginMemberResponse> login(@RequestBody LoginMemberRequest loginMemberRequest);


	@PostMapping("/refresh")
	ResponseEntity<GetNewTokenResponse> newToken(@RequestBody GetNewTokenRequest getNewTokenRequest);
}
package store.novabook.front.api.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.ApiResponse;
import store.novabook.front.api.member.dto.CreateMemberRequest;
import store.novabook.front.api.member.dto.CreateMemberResponse;
import store.novabook.front.api.member.dto.LoginMemberRequest;
import store.novabook.front.api.member.dto.LoginMemberResponse;

@FeignClient(name = "memberClient", url = "http://localhost:9777/auth")
public interface MemberClient {

	@PostMapping
	ApiResponse<CreateMemberResponse> createMember(@RequestBody CreateMemberRequest createMemberRequest);

	@PostMapping("/login")
	ResponseEntity<LoginMemberResponse> login(@RequestBody LoginMemberRequest loginMemberRequest);


}

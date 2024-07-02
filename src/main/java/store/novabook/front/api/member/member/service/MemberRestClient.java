package store.novabook.front.api.member.member.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.member.member.dto.request.DuplicateEmailRequest;
import store.novabook.front.api.member.member.dto.request.DuplicateLoginIdRequest;
import store.novabook.front.api.member.member.dto.response.DuplicateResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "memberRestClient")
public interface MemberRestClient {

	@PostMapping("/loginId/is-creatable")
	ApiResponse<DuplicateResponse> isDuplicateLoginId(@RequestBody DuplicateLoginIdRequest request);

	@PostMapping("/email/is-creatable")
	ApiResponse<DuplicateResponse> isDuplicateEmail(@RequestBody DuplicateEmailRequest request);

}

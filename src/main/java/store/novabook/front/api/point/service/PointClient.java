package store.novabook.front.api.point.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import store.novabook.front.api.point.dto.GetMemberPointResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "pointClient")
public interface PointClient {

	@GetMapping("/member")
	ApiResponse<GetMemberPointResponse> getMemberPoint();
}

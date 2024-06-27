package store.novabook.front.api.point.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.api.point.dto.GetMemberPointResponse;

@FeignClient(name = "pointClient", url = "http://localhost:9777/api/v1/store/point/histories")
public interface PointClient {

	@GetMapping("/member")
	public ApiResponse<GetMemberPointResponse> getMemberPoint();
}

package store.novabook.front.api.point.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.api.point.dto.request.CreatePointPolicyRequest;
import store.novabook.front.api.point.dto.response.CreatePointPolicyResponse;
import store.novabook.front.api.point.dto.response.GetPointPolicyResponse;

@FeignClient(name = "pointPolicyClient")
public interface PointPolicyClient {

	//pageable로 모두 가져오기
	@GetMapping
	PageResponse<GetPointPolicyResponse> getPointPolicyAll(@RequestParam int page, @RequestParam int size);

	//마지막 정책 가져오기
	@GetMapping("/latest")
	ApiResponse<GetPointPolicyResponse> getLatestPoint();

	//생성
	@PostMapping
	ApiResponse<CreatePointPolicyResponse> createPointPolicy(@RequestBody CreatePointPolicyRequest request);
}

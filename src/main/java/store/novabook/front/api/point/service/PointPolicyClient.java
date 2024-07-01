package store.novabook.front.api.point.service;

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

	@GetMapping
	PageResponse<GetPointPolicyResponse> getPointPolicyAll(@RequestParam int page, @RequestParam int size);

	@GetMapping("/latest")
	ApiResponse<GetPointPolicyResponse> getLatestPoint();

	@PostMapping
	ApiResponse<CreatePointPolicyResponse> createPointPolicy(@RequestBody CreatePointPolicyRequest request);
}

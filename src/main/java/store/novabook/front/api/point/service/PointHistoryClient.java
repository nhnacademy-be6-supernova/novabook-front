package store.novabook.front.api.point.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.point.dto.GetMemberPointResponse;
import store.novabook.front.api.point.dto.request.GetPointHistoryRequest;
import store.novabook.front.api.point.dto.response.GetPointHistoryListResponse;
import store.novabook.front.api.point.dto.response.GetPointHistoryResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/point/histories", contextId = "pointHistoryClient")
public interface PointHistoryClient {

	@PostMapping("/member")
	ApiResponse<GetPointHistoryListResponse> getPointHistoryListByMemberId(
		@RequestBody GetPointHistoryRequest getPointHistoryRequest);

	@GetMapping
	PageResponse<GetPointHistoryResponse> getPointHistoryList(@RequestParam int page, @RequestParam int size);

	@GetMapping("/member/point")
	ApiResponse<GetMemberPointResponse> getPointTotalByMemberId();

	@GetMapping("/member")
	PageResponse<GetPointHistoryResponse> getPointHistoryByMemberIdPage(@RequestParam int page, @RequestParam int size);

}

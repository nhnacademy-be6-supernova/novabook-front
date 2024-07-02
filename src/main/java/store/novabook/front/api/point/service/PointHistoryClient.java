package store.novabook.front.api.point.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.point.dto.request.GetPointHistoryRequest;
import store.novabook.front.api.point.dto.response.GetPointHistoryListResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "pointHistoryClient")
public interface PointHistoryClient {

	@PostMapping("/member")
	ApiResponse<GetPointHistoryListResponse> getPointHistoryListByMemberId(
		@RequestBody GetPointHistoryRequest getPointHistoryRequest);

}

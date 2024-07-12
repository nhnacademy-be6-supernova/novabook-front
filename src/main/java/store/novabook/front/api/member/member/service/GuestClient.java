package store.novabook.front.api.member.member.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.store.order.dto.GetGuestOrderHistoryRequest;
import store.novabook.front.store.order.dto.GetOrderDetailResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/guest", contextId = "guestClient")
public interface GuestClient {

	@PostMapping
	ApiResponse<GetOrderDetailResponse> getOrder(@RequestBody GetGuestOrderHistoryRequest request);
}

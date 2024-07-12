package store.novabook.front.api.delivery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.delivery.dto.request.CreateDeliveryFeeRequest;
import store.novabook.front.api.delivery.dto.response.CreateDeliveryFeeResponse;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/orders/delivery-fee", contextId = "deliveryFeeClient")
public interface DeliveryFeeClient {
	@GetMapping
	PageResponse<GetDeliveryFeeResponse> getDeliveryAllPage(@RequestParam int page, @RequestParam int size);

	@PostMapping
	ApiResponse<CreateDeliveryFeeResponse> createDeliveryFee(@RequestBody CreateDeliveryFeeRequest request);

	@GetMapping("/recent")
	ApiResponse<GetDeliveryFeeResponse> getRecentDeliveryFee();
}

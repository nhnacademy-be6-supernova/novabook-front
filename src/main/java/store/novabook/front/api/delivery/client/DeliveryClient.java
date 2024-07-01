package store.novabook.front.api.delivery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.delivery.dto.CreateDeliveryFeeRequest;
import store.novabook.front.api.delivery.dto.CreateDeliveryFeeResponse;
import store.novabook.front.api.delivery.dto.GetDeliveryFeeResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

@FeignClient(name = "deliveryClient")
public interface DeliveryClient {
	@GetMapping
	PageResponse<GetDeliveryFeeResponse> getDeliveryAllPage(@RequestParam int page, @RequestParam int size);

	@PostMapping
	ApiResponse<CreateDeliveryFeeResponse> createDeliveryFee(@RequestBody CreateDeliveryFeeRequest request);

}

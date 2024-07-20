package store.novabook.front.api.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.order.dto.request.PaymentRequest;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/orders/saga", contextId = "ordersSagaClient")
public interface OrdersSagaClient {
	@PostMapping
	ApiResponse<Void> createOrders(@RequestBody PaymentRequest request);
}

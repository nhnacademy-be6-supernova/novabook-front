package store.novabook.front.api.order.service;

import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import store.novabook.front.api.order.dto.request.PaymentRequest;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name="orderSagaClient")
public interface OrdersSagaClient {
	@PostMapping
	ApiResponse<JSONObject> createOrders(@RequestBody PaymentRequest request);
}

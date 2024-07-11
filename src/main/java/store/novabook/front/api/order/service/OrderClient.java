package store.novabook.front.api.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import store.novabook.front.api.order.dto.request.CreateOrdersRequest;
import store.novabook.front.api.order.dto.response.CreateOrderResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/order", contextId = "orderClient")
public interface OrderClient {
	// ResponseEntity<CreateOrderResponse> createOrders(@Valid @RequestBody CreateOrdersRequest request);

}

package store.novabook.front.api.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import store.novabook.front.api.order.dto.request.CreateOrdersRequest;
import store.novabook.front.api.order.dto.response.CreateOrderResponse;

@FeignClient(name="orderClient")
public interface OrderClient {
	ResponseEntity<CreateOrderResponse> createOrders(@Valid @RequestBody CreateOrdersRequest request);
}

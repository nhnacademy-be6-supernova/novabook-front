package store.novabook.front.api.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


import jakarta.validation.Valid;
import store.novabook.front.api.order.dto.CreateOrderResponse;
import store.novabook.front.api.order.dto.CreateOrdersRequest;

@FeignClient(name="orderClient")
public interface OrderClient {
	ResponseEntity<CreateOrderResponse> createOrders(@Valid @RequestBody CreateOrdersRequest request);
}

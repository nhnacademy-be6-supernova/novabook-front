package store.novabook.front.api.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.novabook_front.api.order.dto.CreateOrdersRequest;

import jakarta.validation.Valid;
import store.novabook.front.api.order.dto.CreateOrderResponse;

@FeignClient(name="orderClient", url = "http://localhost:8090")
public interface OrderClient {
	public ResponseEntity<CreateOrderResponse> createOrders(@Valid @RequestBody CreateOrdersRequest request);
}

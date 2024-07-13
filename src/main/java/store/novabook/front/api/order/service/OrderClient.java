package store.novabook.front.api.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.store.order.dto.GetOrderDetailResponse;
import store.novabook.front.store.order.dto.GetOrdersAdminResponse;
import store.novabook.front.store.order.dto.GetOrdersResponse;
import store.novabook.front.store.order.dto.UpdateOrdersAdminRequest;

@FeignClient(name = "gateway-service", path = "/api/v1/store/orders", contextId = "orderClient")
public interface OrderClient {
	// ResponseEntity<CreateOrderResponse> createOrders(@Valid @RequestBody CreateOrdersRequest request);

	@GetMapping("/admin")
	PageResponse<GetOrdersAdminResponse> getOrdersAdmin(@RequestParam("page") int page, @RequestParam("size") int size);

	@GetMapping("/{id}")
	ApiResponse<GetOrdersResponse> getOrders(@PathVariable Long id);

	@PutMapping("/{id}")
	ApiResponse<Void> update(@PathVariable Long id, @Valid @RequestBody UpdateOrdersAdminRequest request);

	@GetMapping("/guest/{ordersId}")
	ApiResponse <GetOrderDetailResponse> getOrdersGuest(@PathVariable Long ordersId);


}

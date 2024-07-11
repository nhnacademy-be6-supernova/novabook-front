package store.novabook.front.api.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.order.dto.response.GetOrdersBookReviewIdResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.store.order.dto.GetOrderDetailResponse;
import store.novabook.front.store.order.dto.GetOrdersBookResponse;

@FeignClient(name = "ordersBookClient")
public interface OrdersBookClient {
	@GetMapping("/members")
	PageResponse<GetOrdersBookReviewIdResponse> getOrdersBookReviewId(@RequestParam int page, @RequestParam int size);

	@GetMapping("/member/orders")
	PageResponse<GetOrdersBookResponse> getOrdersBookAll(@RequestParam int page, @RequestParam int size);

	@GetMapping("/detail/{ordersId}")
	ApiResponse<GetOrderDetailResponse> getOrderDetails(@PathVariable Long ordersId);
}

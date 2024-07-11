package store.novabook.front.api.order.service;

import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import store.novabook.front.api.order.dto.request.CreateOrdersRequest;
import store.novabook.front.api.order.dto.request.TossPaymentRequest;
import store.novabook.front.api.order.dto.response.CreateOrderResponse;
import store.novabook.front.common.response.ApiResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/order", contextId = "orderClient")
public interface OrderClient {
}

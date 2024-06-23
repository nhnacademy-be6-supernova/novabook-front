package com.nhnacademy.novabook_front.api.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhnacademy.novabook_front.api.ApiResponse;

@FeignClient(name = "WrappingPaperClient", url = "http://localhost:8090/api/v1/store/orders/wrapping/paper")
public interface WrappingPaperClient {

	@GetMapping
	ApiResponse<GetWrappingPaperAllResponse> getWrappingPaperAll();
}

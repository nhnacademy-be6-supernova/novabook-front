package com.nhnacademy.novabook_front.api.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.novabook_front.api.ApiResponse;
import com.nhnacademy.novabook_front.api.PageResponse;
import com.nhnacademy.novabook_front.api.order.dto.request.CreateWrappingPaperRequest;
import com.nhnacademy.novabook_front.api.order.dto.request.UpdateWrappingPaperRequest;
import com.nhnacademy.novabook_front.api.order.dto.response.CreateWrappingPaperResponse;
import com.nhnacademy.novabook_front.api.order.dto.response.GetWrappingPaperAllResponse;
import com.nhnacademy.novabook_front.api.order.dto.response.GetWrappingPaperResponse;

@FeignClient(name = "WrappingPaperClient", url = "http://localhost:8090/api/v1/store/orders/wrapping/paper")
public interface WrappingPaperClient {


	//리스트 전체 조회
	@GetMapping
	ApiResponse<GetWrappingPaperAllResponse> getWrappingPaperAllList();

	//페이지 전체 조회
	@GetMapping
	PageResponse<GetWrappingPaperResponse> getWrappingPaperAllPage(@RequestParam int page, @RequestParam int size);

	//단건 조회
	@GetMapping("/{id}")
	ApiResponse<GetWrappingPaperResponse> getWrappingPaper(@PathVariable Long id);

	//생성
	@PostMapping
	ApiResponse<CreateWrappingPaperResponse> createWrappingPaper(@RequestBody CreateWrappingPaperRequest request);

	//수정
	@PutMapping("/{id}")
	ApiResponse<Void> putWrappingPaper(@RequestBody UpdateWrappingPaperRequest request, @PathVariable Long id);


}

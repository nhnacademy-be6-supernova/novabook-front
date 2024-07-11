package store.novabook.front.api.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.order.dto.request.CreateWrappingPaperRequest;
import store.novabook.front.api.order.dto.request.UpdateWrappingPaperRequest;
import store.novabook.front.api.order.dto.response.CreateWrappingPaperResponse;
import store.novabook.front.api.order.dto.response.GetWrappingPaperAllResponse;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/orders/wrapping-papers", contextId = "wrappingPaperClient")
public interface WrappingPaperClient {

	@GetMapping
	ApiResponse<GetWrappingPaperAllResponse> getWrappingPaperAllList();

	@GetMapping
	PageResponse<GetWrappingPaperResponse> getWrappingPaperAllPage(@RequestParam int page, @RequestParam int size);

	@GetMapping("/{id}")
	ApiResponse<GetWrappingPaperResponse> getWrappingPaper(@PathVariable Long id);

	@PostMapping
	ApiResponse<CreateWrappingPaperResponse> createWrappingPaper(@RequestBody CreateWrappingPaperRequest request);

	@PutMapping("/{id}")
	ApiResponse<Void> putWrappingPaper(@RequestBody UpdateWrappingPaperRequest request, @PathVariable Long id);
}

package store.novabook.front.api.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.api.order.dto.request.CreateWrappingPaperRequest;
import store.novabook.front.api.order.dto.request.UpdateWrappingPaperRequest;
import store.novabook.front.api.order.dto.response.CreateWrappingPaperResponse;
import store.novabook.front.api.order.dto.response.GetWrappingPaperAllResponse;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;

@FeignClient(name = "WrappingPaperClient", url = "http://localhost:9777/api/v1/store/orders/wrapping/paper")
public interface WrappingPaperClient {


	//리스트 전체 조회
	@GetMapping
	ApiResponse<GetWrappingPaperAllResponse> getWrappingPaperAllList();

	//페이지 전체 조회
	@GetMapping("/pageable")
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

package store.novabook.front.api.tag.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.api.tag.dto.request.CreateTagRequest;
import store.novabook.front.api.tag.dto.response.CreateTagResponse;
import store.novabook.front.api.tag.dto.response.GetTagListResponse;
import store.novabook.front.api.tag.dto.response.GetTagResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

@FeignClient(name = "gateway-service", path = "/api/v1/store/tags", contextId = "tagClient")
public interface TagClient {
	@PostMapping
	ApiResponse<CreateTagResponse> createTag(@RequestBody CreateTagRequest createTagRequest);

	@GetMapping
	PageResponse<GetTagResponse> getTagAll(@RequestParam int page, @RequestParam int size);

	@GetMapping
	ApiResponse<GetTagListResponse> getTagAllList();

	@DeleteMapping("/{id}")
	void deleteTag(@PathVariable Long id);
}

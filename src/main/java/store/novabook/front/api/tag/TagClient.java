package store.novabook.front.api.tag;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.api.tag.dto.CreateTagRequest;
import store.novabook.front.api.tag.dto.CreateTagResponse;
import store.novabook.front.api.tag.dto.GetTagListResponse;
import store.novabook.front.api.tag.dto.GetTagResponse;

@FeignClient(name = "tagClient", url = "http://localhost:8090/api/v1/store/tags")
public interface TagClient {
	@PostMapping
	ApiResponse<CreateTagResponse> createTag(@RequestBody CreateTagRequest createTagRequest);

	@GetMapping
	PageResponse<GetTagResponse> getTagAll(@RequestParam int page, @RequestParam int size);

	@GetMapping("/list")
	ApiResponse<GetTagListResponse> getTagAllList();

	@DeleteMapping("/{id}")
	void deleteTag(@PathVariable Long id);
}

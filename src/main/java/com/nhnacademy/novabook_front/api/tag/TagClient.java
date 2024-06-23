package com.nhnacademy.novabook_front.api.tag;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.novabook_front.api.ApiResponse;
import com.nhnacademy.novabook_front.api.PageResponse;
import com.nhnacademy.novabook_front.api.tag.dto.CreateTagRequest;
import com.nhnacademy.novabook_front.api.tag.dto.CreateTagResponse;
import com.nhnacademy.novabook_front.api.tag.dto.GetTagResponse;

@FeignClient(name = "tagClient", url = "http://localhost:8090/api/v1/store/tags")
public interface TagClient {
	@PostMapping
	ApiResponse<CreateTagResponse> createTag(@RequestBody CreateTagRequest createTagRequest);

	@GetMapping
	PageResponse<GetTagResponse> getTagAll(@RequestParam int page, @RequestParam int size);

	@DeleteMapping("/{id}")
	void deleteTag(@PathVariable Long id);
}

package com.nhnacademy.novabook_front.api.tag.service;

import com.nhnacademy.novabook_front.api.PageResponse;
import com.nhnacademy.novabook_front.api.tag.dto.CreateTagRequest;
import com.nhnacademy.novabook_front.api.tag.dto.CreateTagResponse;
import com.nhnacademy.novabook_front.api.tag.dto.GetTagResponse;

public interface TagService {

	CreateTagResponse createTags(CreateTagRequest tagRequest);
	PageResponse<GetTagResponse> getTags(int page, int size);
	void deleteTag(Long tagId);

}

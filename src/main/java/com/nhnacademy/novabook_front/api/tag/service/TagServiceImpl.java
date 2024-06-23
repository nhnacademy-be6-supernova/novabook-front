package com.nhnacademy.novabook_front.api.tag.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nhnacademy.novabook_front.api.ApiResponse;
import com.nhnacademy.novabook_front.api.PageResponse;
import com.nhnacademy.novabook_front.api.tag.TagClient;
import com.nhnacademy.novabook_front.api.tag.dto.CreateTagRequest;
import com.nhnacademy.novabook_front.api.tag.dto.CreateTagResponse;
import com.nhnacademy.novabook_front.api.tag.dto.GetTagResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
	private final TagClient tagClient;

	@Override
	public CreateTagResponse createTags(CreateTagRequest tagRequest) {
		ApiResponse<CreateTagResponse> responseEntity = tagClient.createTag(tagRequest);
		return responseEntity.getBody();
	}

	@Override
	public PageResponse<GetTagResponse> getTags(int page, int size) {
		return tagClient.getTagAll(page, size);

	}

	@Override
	public void deleteTag(Long tagId) {
		tagClient.deleteTag(tagId);
	}
}

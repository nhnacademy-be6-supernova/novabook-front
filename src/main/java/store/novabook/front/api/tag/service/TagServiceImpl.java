package store.novabook.front.api.tag.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.api.ApiResponse;
import store.novabook.front.api.PageResponse;
import store.novabook.front.api.tag.TagClient;
import store.novabook.front.api.tag.dto.CreateTagRequest;
import store.novabook.front.api.tag.dto.CreateTagResponse;
import store.novabook.front.api.tag.dto.GetTagResponse;

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

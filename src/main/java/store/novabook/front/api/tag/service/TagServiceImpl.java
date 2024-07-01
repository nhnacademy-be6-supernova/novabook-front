package store.novabook.front.api.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.api.tag.dto.request.CreateTagRequest;
import store.novabook.front.api.tag.dto.response.CreateTagResponse;
import store.novabook.front.api.tag.dto.response.GetTagListResponse;
import store.novabook.front.api.tag.dto.response.GetTagResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;


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
	public List<GetTagResponse> getTagList() {
		GetTagListResponse response = tagClient.getTagAllList().getBody();
		return response.getTagResponseList();
	}

	@Override
	public void deleteTag(Long tagId) {
		tagClient.deleteTag(tagId);
	}
}

package store.novabook.front.api.tag.dto;

import java.util.List;

public record GetTagListResponse(
	List<GetTagResponse> getTagResponseList) {
}
package store.novabook.front.api.tag.dto.response;

import java.util.List;

public record GetTagListResponse(
	List<GetTagResponse> getTagResponseList) {
}
package store.novabook.front.api.tag.dto.request;

import lombok.Builder;

@Builder
public record CreateTagRequest(
	String name) {
}

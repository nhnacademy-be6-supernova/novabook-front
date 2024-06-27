package store.novabook.front.api.tag.dto;

import lombok.Builder;

@Builder
public record CreateTagRequest(
	String name) {
}

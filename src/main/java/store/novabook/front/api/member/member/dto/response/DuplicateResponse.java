package store.novabook.front.api.member.member.dto.response;

import lombok.Builder;

@Builder
public record DuplicateResponse(
	Boolean isDuplicate) {
}

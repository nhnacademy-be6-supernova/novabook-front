package store.novabook.front.api.member.dto;

import lombok.Builder;

@Builder
public record CheckDuplicateLoginIdResponse(
	String loginId
) {
}

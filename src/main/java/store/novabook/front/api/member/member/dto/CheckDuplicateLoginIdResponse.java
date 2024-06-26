package store.novabook.front.api.member.member.dto;

import lombok.Builder;

@Builder
public record CheckDuplicateLoginIdResponse(
	String loginId
) {
}

package store.novabook.front.api.member.address.dto.response;

import lombok.Builder;

@Builder
public record ExceedResponse(
	Boolean isExceed
) {
}

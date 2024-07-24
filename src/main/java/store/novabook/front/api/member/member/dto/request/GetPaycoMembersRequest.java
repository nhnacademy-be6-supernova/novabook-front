package store.novabook.front.api.member.member.dto.request;

import lombok.Builder;

@Builder
public record GetPaycoMembersRequest(
	String paycoId
) {
}

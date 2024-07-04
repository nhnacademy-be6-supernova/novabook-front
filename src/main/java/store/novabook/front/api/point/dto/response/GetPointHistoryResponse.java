package store.novabook.front.api.point.dto.response;

import lombok.Builder;

@Builder
public record GetPointHistoryResponse(
	String pointContent,
	long pointAmount) {
}

package store.novabook.front.api.point.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record GetPointHistoryResponse(
	String pointContent,
	Long pointAmount,
	LocalDateTime createdAt
) {
}

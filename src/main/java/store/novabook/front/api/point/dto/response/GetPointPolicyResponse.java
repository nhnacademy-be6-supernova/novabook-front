package store.novabook.front.api.point.dto.response;

import lombok.Builder;

@Builder
public record GetPointPolicyResponse(
	long reviewPointRate,
	long basicPoint,
	long registerPoint) {
}

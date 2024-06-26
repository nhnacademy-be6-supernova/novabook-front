package store.novabook.front.api.point.dto.request;

import lombok.Builder;

@Builder
public record CreatePointPolicyRequest(
	Long reviewPointRate,
	Long basicPoint,
	Long registerPoint) {
}

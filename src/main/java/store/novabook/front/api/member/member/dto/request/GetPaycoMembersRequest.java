package store.novabook.front.api.member.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record GetPaycoMembersRequest(
	@NotBlank
	String paycoId
) {
}

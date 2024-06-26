package store.novabook.front.api.member.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginMemberRequest(
	@NotBlank
	String loginId,
	@NotBlank
	String loginPassword) {
}


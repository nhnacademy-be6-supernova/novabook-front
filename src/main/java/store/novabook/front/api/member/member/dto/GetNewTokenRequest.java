package store.novabook.front.api.member.member.dto;

import jakarta.validation.constraints.NotBlank;

public record GetNewTokenRequest(
	@NotBlank
	String refreshToken
) {
}

package store.novabook.front.api.member.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record IsExpireAccessTokenRequest(
	@NotBlank
	String accessToken
) {
}

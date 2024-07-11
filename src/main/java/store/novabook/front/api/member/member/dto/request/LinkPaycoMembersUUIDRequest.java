package store.novabook.front.api.member.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LinkPaycoMembersUUIDRequest(
	@NotBlank
	String accessToken,
	@NotBlank
	String oauthId
) {
}

package store.novabook.front.api.member.member.dto.response;

import jakarta.validation.constraints.NotBlank;

public record GetMembersRoleRequest(
	@NotBlank
	String accessToken
) {
}

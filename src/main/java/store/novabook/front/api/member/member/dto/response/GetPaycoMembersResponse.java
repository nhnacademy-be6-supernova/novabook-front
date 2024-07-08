package store.novabook.front.api.member.member.dto.response;

public record GetPaycoMembersResponse(
	String accessToken,
	String refreshToken
) {
}

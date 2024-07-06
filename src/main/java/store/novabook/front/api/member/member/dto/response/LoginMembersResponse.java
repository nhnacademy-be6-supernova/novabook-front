package store.novabook.front.api.member.member.dto.response;

public record LoginMembersResponse(
	String accessToken,
	String refreshToken
) {
}

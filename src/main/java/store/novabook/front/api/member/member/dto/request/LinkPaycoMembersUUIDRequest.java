package store.novabook.front.api.member.member.dto.request;


public record LinkPaycoMembersUUIDRequest(
	String accessToken,
	String oauthId
) {
}

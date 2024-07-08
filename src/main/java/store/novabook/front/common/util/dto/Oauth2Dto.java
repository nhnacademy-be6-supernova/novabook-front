package store.novabook.front.common.util.dto;

public record Oauth2Dto(
	String clientId,
	String clientSecret,
	String redirectUri,
	String tokenUri
) {
}

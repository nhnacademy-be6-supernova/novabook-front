package store.novabook.front.common.util.dto;

public record DatabaseConfigDto(
	String url,
	String username,
	String password
) {
}

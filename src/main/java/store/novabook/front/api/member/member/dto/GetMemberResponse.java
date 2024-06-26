package store.novabook.front.api.member.dto;

public record GetMemberResponse(
	Long id,
	String loginId,
	String name,
	String email
) {}
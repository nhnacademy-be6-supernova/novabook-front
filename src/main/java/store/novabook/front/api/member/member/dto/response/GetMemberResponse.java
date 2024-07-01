package store.novabook.front.api.member.member.dto.response;

public record GetMemberResponse(
	Long id,
	String loginId,
	Integer birthYear,
	Integer birthMonth,
	Integer birthDay,
	String number,
	String name,
	String email
) {
}
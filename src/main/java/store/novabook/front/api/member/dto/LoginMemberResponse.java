package store.novabook.front.api.member.dto;

public record LoginMemberResponse(boolean success, Long memberId, String name) {
}

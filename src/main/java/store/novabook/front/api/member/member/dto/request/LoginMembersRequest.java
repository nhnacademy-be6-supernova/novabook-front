package store.novabook.front.api.member.member.dto.request;


public record LoginMembersRequest(
	String loginId,
	String loginPassword) {
}


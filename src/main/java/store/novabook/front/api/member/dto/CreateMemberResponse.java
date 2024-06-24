package store.novabook.front.api.member.dto;

import lombok.Builder;
import store.novabook.front.api.member.domain.Member;

@Builder
public record CreateMemberResponse(Long id) {
	public static CreateMemberResponse fromEntity(Member member) {
		return CreateMemberResponse.builder()
			.id(member.getId())
			.build();
	}
}

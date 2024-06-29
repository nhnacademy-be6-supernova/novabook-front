package store.novabook.front.api.member.grade.dto;

import lombok.Builder;

@Builder
public record GetMemberGradeResponse(
	String name) {
}

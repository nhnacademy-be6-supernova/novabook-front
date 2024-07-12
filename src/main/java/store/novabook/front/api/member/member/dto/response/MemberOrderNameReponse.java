package store.novabook.front.api.member.member.dto.response;

import lombok.Builder;

@Builder
public record MemberOrderNameReponse (
	String name,
	String orderNumber
){
}

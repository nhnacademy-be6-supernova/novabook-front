package store.novabook.front.api.member.dto;

import lombok.Builder;

@Builder
public record LoginMemberRequest (String username, String password){
}


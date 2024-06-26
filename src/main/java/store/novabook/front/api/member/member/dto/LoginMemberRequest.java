package store.novabook.front.api.member.member.dto;

import lombok.Builder;

public record LoginMemberRequest(String loginId, String loginPassword) {
}


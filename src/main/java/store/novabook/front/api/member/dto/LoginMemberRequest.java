package store.novabook.front.api.member.dto;

import lombok.Builder;

public record LoginMemberRequest(String loginId, String loginPassword) {
}


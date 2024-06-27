package store.novabook.front.api.member.address.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class MemberAddress {

	private Long id;

	private String nickname;

	private String memberAddressDetail;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Long streetAddressId;

	private Long memberId;
}

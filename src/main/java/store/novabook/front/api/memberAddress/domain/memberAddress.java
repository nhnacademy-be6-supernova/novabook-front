package store.novabook.front.api.memberAddress.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class memberAddress {

	private Long id;

	private String nickname;

	private String memberAddressDetail;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Long streetAddressId;

	private Long memberId;
}

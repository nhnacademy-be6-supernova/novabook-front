package store.novabook.front.api.member.address.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StreetAddress {
	private Long id;
	private String zipcode;
	private String streetAddresses;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

package store.novabook.front.store.order.dto;

import lombok.Builder;

@Builder
public record OrderAddressInfo(
	String zipCode,
	String streetAddress,
	String detailAddress
) {}

package com.nhnacademy.novabook_front.api.memberAddress.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class StreetAddress {
	private Long id;
	private String zipcode;
	private String streetAddress;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

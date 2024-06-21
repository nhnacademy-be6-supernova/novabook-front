package com.nhnacademy.novabook_front.api.member;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record CreateMemberRequest(
	String loginId,
	String loginPassword,
	String name,
	String number,
	String email,
	String emailDomain,
	Integer birthYear,
	Integer birthMonth,
	Integer birthDay,
	String address) {

	public String getEmailFull() {
		return email + "@" + emailDomain;
	}

}


package store.novabook.front.api.member.member.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Member {

	private Long id;

	private String loginId;

	private String loginPassword;

	private String name;

	private String number;

	private String email;

	private LocalDateTime birth;

	private Long point;

	private LocalDateTime latestLoginAt;

	private Long totalAmount;

	private int authentication;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Long memberGradeId;

	private Long memberStatusId;

	private Long usersId;
}

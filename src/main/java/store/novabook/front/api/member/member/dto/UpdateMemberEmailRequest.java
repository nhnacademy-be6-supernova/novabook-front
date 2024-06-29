package store.novabook.front.api.member.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateMemberEmailRequest(
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Size(max = 100, message = "최대 100자까지 가능합니다.")
	String email,

	@NotBlank(message = "이메일 도메인은 필수 입력 값입니다.")
	@Size(max = 50, message = "최대 50자까지 가능합니다.")
	String emailDomain
) {
}

package store.novabook.front.api.member.member.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateMemberRequest(
	@Size(max = 50, message = "최대 50자까지 가능합니다.")
	String name,

	@Pattern(regexp = "^\\d{11}$")
	@Size(max = 11, message = "최대 11자까지 가능합니다.")
	String number) {
}

package store.novabook.front.api.member.address.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateMemberAddressRequest(
	@NotBlank(message = "우편번호는 필수 입력값입니다.")
	String zipcode,
	@NotBlank(message = "도로명주소는 필수 입력값입니다.")
	String streetAddress,
	@NotBlank(message = "배송지는 필수 입력값입니다.")
	String nickname,
	@NotBlank(message = "상세주소는 필수 입력값입니다.")
	String memberAddressDetail
) {

}

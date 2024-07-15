package store.novabook.front.api.cart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CartBookDTO(
	@NotNull
	Long bookId,

	@NotBlank
	@Size(max = 255, message = "제목은 최대 255자만 입력 할 수 있습니다.")
	String title,

	String image,

	@NotNull(message = "가격값이 null 입니다.")
	@Positive
	Long price,

	@NotNull(message = "할인값이 null 입니다.")
	@Positive
	Long discountPrice,

	@Positive(message = "수량은 음수값이 될 수 없습니다.")
	Integer quantity,

	@NotNull(message = "포장여부값이 없습니다.")
	boolean isPackaged,

	@NotNull(message = "책 상태값이 없습니다.")
	Long bookStatusId

) {
	public static CartBookDTO update(Long bookId, Integer quantity, CartBookDTO request) {
		return CartBookDTO.builder()
			.bookId(bookId)
			.title(request.title())
			.image(request.image())
			.price(request.price())
			.discountPrice(request.discountPrice())
			.quantity(quantity)
			.bookStatusId(request.bookStatusId())
			.isPackaged(request.isPackaged())
			.build();
	}
}

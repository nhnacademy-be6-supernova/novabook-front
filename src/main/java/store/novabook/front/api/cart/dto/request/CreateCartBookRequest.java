package store.novabook.front.api.cart.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record CreateCartBookRequest(
	@NotNull
	Long bookId,

	@NotEmpty
	String title,

	String image,

	@NotNull
	Integer price,

	@NotNull
	Integer discountPrice,

	@Positive
	Integer quantity
) {
	public static CreateCartBookRequest update(Long bookId, Integer quantity, CreateCartBookRequest request) {
		return CreateCartBookRequest.builder()
			.bookId(bookId)
			.title(request.title())
			.image(request.image())
			.price(request.price())
			.discountPrice(request.discountPrice())
			.quantity(quantity)
			.build();
	}
}

package store.novabook.front.api.cart.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record GetCartResponse(
	Long cartId,
	List<GetCartBookResponse> cartBookList
) {
	public static GetCartResponse of() {
		return GetCartResponse.builder().cartBookList(new ArrayList<>()).build();
	}
}

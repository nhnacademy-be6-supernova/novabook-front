package store.novabook.front.api.cart.dto.request;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import store.novabook.front.api.cart.dto.response.GetCartBookResponse;

@Builder
public record CreateCartBookListRequest(
	List<CreateCartBookRequest> cartBookList
) {
	// public static CreateCartBookListRequest of(Long cartId, List<GetCartBookResponse> responses) {
	// 	return CreateCartBookListRequest.builder()
	// 		.cartId(cartId)
	// 		.cartBookList(getBooks(responses))
	// 		.build();
	// }
	//
	// public static List<CreateCartBookRequest> getBooks(List<GetCartBookResponse> responses) {
	// 	List<CreateCartBookRequest> result = new ArrayList<>(responses.size());
	// 	for (GetCartBookResponse respons : responses) {
	// 		result.add(CreateCartBookRequest.of(respons.bookId(),respons.quantity()));
	// 	}
	// 	return result;
	}

	// public static CreateCartBookListRequest of(Long cartId, List<CreateCartBookRequest> cartBookList) {
	// 	return new CreateCartBookListRequest(cartId, cartBookList);
	// }


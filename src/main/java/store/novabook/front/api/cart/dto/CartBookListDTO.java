package store.novabook.front.api.cart.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartBookListDTO {
	@NotNull(message = "cartBookList가 null입니다.")
	List<CartBookDTO> cartBookList;

	public CartBookListDTO(List<CartBookDTO> cartBookList) {
		this.cartBookList = cartBookList;
	}
}



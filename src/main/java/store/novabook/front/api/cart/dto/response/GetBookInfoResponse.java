package store.novabook.front.api.cart.dto.response;

import java.util.List;

import store.novabook.front.api.cart.dto.CartBookInfoDto;

public record GetBookInfoResponse(List<CartBookInfoDto> bookInfo) {
}

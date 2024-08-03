package store.novabook.front.api.cart.dto.request;

import java.util.List;

public record GetBookInfoRequest(List<Long> bookIds) {
}

package store.novabook.front.api.category.dto.response;

import java.util.List;

public record GetCategoryIdsByBookIdResponse(List<Long> categoryIds) {
}

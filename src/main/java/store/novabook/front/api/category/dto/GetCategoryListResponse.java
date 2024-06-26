package store.novabook.front.api.category.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record GetCategoryListResponse(List<GetCategoryResponse> categories) {
}

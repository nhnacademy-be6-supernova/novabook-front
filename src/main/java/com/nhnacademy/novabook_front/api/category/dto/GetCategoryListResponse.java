package com.nhnacademy.novabook_front.api.category.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record GetCategoryListResponse(GetCategoryResponse topCategories, List<GetCategoryResponse> subCategories) {

}

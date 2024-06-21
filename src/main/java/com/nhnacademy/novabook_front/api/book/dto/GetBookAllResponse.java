package com.nhnacademy.novabook_front.api.book.dto;

import lombok.Builder;
@Builder
public record GetBookAllResponse(Long id,
								 Long bookStatusId,
								 String title,
								 String bookIndex,
								 int inventory,
								 Long price,
								 Long discountPrice,
								 boolean isPackaged
) {

}





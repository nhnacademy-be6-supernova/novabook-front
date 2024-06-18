package com.nhnacademy.novabook_front.api.book;

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





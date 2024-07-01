package store.novabook.front.api.book.dto.response;

import lombok.Builder;

@Builder
public record GetBookAllResponse(
	Long id,
	Long bookStatusId,
	String title,
	int inventory,
	Long price,
	Long discountPrice,
	boolean isPackaged
) {

}





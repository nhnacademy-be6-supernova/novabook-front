package store.novabook.front.api.book.dto.request;

import lombok.Builder;

@Builder
public record ReviewImageDTO(
	String fileName,
	String fileType,
	String data
) {
}

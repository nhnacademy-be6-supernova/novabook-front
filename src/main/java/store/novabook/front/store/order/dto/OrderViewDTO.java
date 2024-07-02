package store.novabook.front.store.order.dto;

import java.util.List;

import lombok.Builder;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;

@Builder
public record OrderViewDTO(
	List<GetWrappingPaperResponse> wrappingPapers
) {
}
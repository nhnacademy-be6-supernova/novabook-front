package store.novabook.front.api.order.service;

import java.util.List;

import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;

public interface WrappingPaperService {
	List<GetWrappingPaperResponse> getWrappingPaperAllList();
}

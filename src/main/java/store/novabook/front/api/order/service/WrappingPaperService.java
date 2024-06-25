package store.novabook.front.api.order.service;

import java.util.List;

import store.novabook.front.api.PageResponse;
import store.novabook.front.api.order.dto.request.CreateWrappingPaperRequest;
import store.novabook.front.api.order.dto.request.UpdateWrappingPaperRequest;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;

public interface WrappingPaperService {
	List<GetWrappingPaperResponse> getWrappingPaperAllList();

	PageResponse<GetWrappingPaperResponse> getWrappingPaperAllPage(int page, int size);

	GetWrappingPaperResponse getWrappingPaper(Long id);

	void createWrappingPaper(CreateWrappingPaperRequest request);

	void updateWrappingPaper(Long id, UpdateWrappingPaperRequest request);
}

package store.novabook.front.api.order.service;

import java.util.List;

import store.novabook.front.api.PageResponse;
import store.novabook.front.api.order.dto.request.CreateWrappingPaperRequest;
import store.novabook.front.api.order.dto.request.UpdateWrappingPaperRequest;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;

public interface WrappingPaperService {
	List<GetWrappingPaperResponse> getWrappingPaperAllList();

	// 페이지 전체 조회
	PageResponse<GetWrappingPaperResponse> getWrappingPaperAllPage(int page, int size);

	//단건 조회
	GetWrappingPaperResponse getWrappingPaper(Long id);

	//생성
	void createWrappingPaper(CreateWrappingPaperRequest request);

	//수정
	void updateWrappingPaper(Long id, UpdateWrappingPaperRequest request);
}

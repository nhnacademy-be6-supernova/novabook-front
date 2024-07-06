package store.novabook.front.api.point.service;

import store.novabook.front.api.point.dto.GetMemberPointResponse;
import store.novabook.front.api.point.dto.response.GetPointHistoryResponse;
import store.novabook.front.common.response.PageResponse;

public interface PointService {
	GetMemberPointResponse getMemberPoint();

	PageResponse<GetPointHistoryResponse> getPointHistories(int page, int size);
}

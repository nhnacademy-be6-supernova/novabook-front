package store.novabook.front.api.point.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.point.dto.GetMemberPointResponse;
import store.novabook.front.api.point.dto.response.GetPointHistoryResponse;
import store.novabook.front.api.point.service.PointHistoryClient;
import store.novabook.front.api.point.service.PointService;
import store.novabook.front.common.response.PageResponse;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
	private final PointHistoryClient pointHistoryClient;

	@Override
	public GetMemberPointResponse getMemberPoint() {
		return pointHistoryClient.getPointTotalByMemberId().getBody();
	}

	@Override
	public PageResponse<GetPointHistoryResponse> getPointHistories(int page, int size) {
		return pointHistoryClient.getPointHistoryByMemberIdPage(page, size);
	}
}

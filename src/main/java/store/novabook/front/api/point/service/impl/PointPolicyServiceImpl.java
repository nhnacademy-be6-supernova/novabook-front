package store.novabook.front.api.point.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.PageResponse;
import store.novabook.front.api.point.client.PointPolicyClient;
import store.novabook.front.api.point.dto.request.CreatePointPolicyRequest;
import store.novabook.front.api.point.dto.response.GetPointPolicyResponse;
import store.novabook.front.api.point.service.PointPolicyService;

@Service
@RequiredArgsConstructor

public class PointPolicyServiceImpl implements PointPolicyService {
	private final PointPolicyClient pointPolicyClient;

	@Override
	public void createPointPolicy(CreatePointPolicyRequest request) {
		pointPolicyClient.createPointPolicy(request);
	}

	@Override
	public PageResponse<GetPointPolicyResponse> getPointPolicyAllPage(int page, int size) {
		return pointPolicyClient.getPointPolicyAll(page, size);
	}

	@Override
	public GetPointPolicyResponse getLatestPointPolicy() {
		return pointPolicyClient.getLatestPoint().getBody();
	}
}

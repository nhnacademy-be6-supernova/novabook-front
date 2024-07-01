package store.novabook.front.api.point.service;

import store.novabook.front.api.point.dto.request.CreatePointPolicyRequest;
import store.novabook.front.api.point.dto.response.GetPointPolicyResponse;
import store.novabook.front.common.response.PageResponse;

/**
 * 포인트 정책 서비스 인터페이스입니다.
 * 포인트 정책 생성, 조회 등의 기능을 제공합니다.
 */
public interface PointPolicyService {

	/**
	 * 포인트 정책을 생성합니다.
	 *
	 * @param request 포인트 정책 생성 요청 객체
	 */
	void createPointPolicy(CreatePointPolicyRequest request);

	/**
	 * 모든 포인트 정책을 페이지 단위로 조회합니다.
	 *
	 * @param page 조회할 페이지 번호
	 * @param size 페이지 당 항목 수
	 * @return 페이지 응답 객체
	 */
	PageResponse<GetPointPolicyResponse> getPointPolicyAllPage(int page, int size);

	/**
	 * 가장 최신의 포인트 정책을 불러옵니다.
	 *
	 * @return 최신 포인트 정책 응답 객체
	 */
	GetPointPolicyResponse getLatestPointPolicy();
}

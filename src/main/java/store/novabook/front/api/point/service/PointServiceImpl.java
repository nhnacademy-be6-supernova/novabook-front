package store.novabook.front.api.point.service;

import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.api.member.MemberClient;
import store.novabook.front.api.point.client.PointClient;
import store.novabook.front.api.point.dto.GetMemberPointResponse;

@Slf4j
@Service
public class PointServiceImpl implements PointService {
	private final PointClient pointClient;
	private final MemberClient memberClient;

	public PointServiceImpl(PointClient pointClient, MemberClient memberClient) {
		this.pointClient = pointClient;
		this.memberClient = memberClient;
	}

	@Override
	public GetMemberPointResponse getMemberPoint() {
		// 임시로 설정한 회원 ID
		Long memberId = 1L;
		long point = 0;

		try {
			// 회원 ID 조회
			Long memberIdResponse = memberClient.getMember(memberId).getBody().id();
			if (memberIdResponse == null) {
				throw new IllegalArgumentException("존재하지 않는 ID 입니다.");
			}

			// 회원 포인트 조회
			GetMemberPointResponse pointResponse = pointClient.getMemberPoint().getBody();
			if (pointResponse != null) {
				point = pointResponse.point();
			}
		} catch (FeignException.FeignClientException e) {
			log.error("Feign 클라이언트 오류 발생", e);
			// Feign 클라이언트에서 발생한 예외 처리
			// 예를 들어 404, 500 등의 상태 코드에 따라 적절히 처리하거나 기본 응답을 반환할 수 있습니다.
		} catch (Exception e) {
			log.error("회원 포인트 처리 중 오류 발생", e);
			// 그 외 예상치 못한 예외 처리
		}

		return new GetMemberPointResponse(point);
	}
}

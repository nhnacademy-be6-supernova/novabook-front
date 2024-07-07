package store.novabook.front.api.member.member.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.coupon.dto.request.DownloadCouponRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponHistoryResponse;
import store.novabook.front.api.coupon.dto.response.GetUsedCouponHistoryResponse;
import store.novabook.front.api.member.coupon.service.MemberCouponClient;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.api.member.member.service.MemberCouponService;
import store.novabook.front.common.exception.FeignClientException;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.messaging.dto.DownloadCouponMessageRequest;

@Service
@RequiredArgsConstructor
public class MemberCouponServiceImpl implements MemberCouponService {

	private final MemberClient memberClient;
	private final MemberCouponClient memberCouponClient;

	@Override
	public GetCouponAllResponse getMyCouponAllWithValid() {
		ApiResponse<GetCouponAllResponse> memberCouponValidByMemberId = memberCouponClient.getMemberCouponValidByMemberId();
		return memberCouponValidByMemberId.getBody();
	}

	@Override
	public Page<GetCouponHistoryResponse> getMyCouponHistoryAll(Pageable pageable) {
		return memberCouponClient.getMemberCouponHistoryByMemberId(pageable.getPageNumber(), pageable.getPageSize())
			.toPage();
	}

	@Override
	public Page<GetUsedCouponHistoryResponse> getMyUsedCouponHistory(Pageable pageable) {
		return memberCouponClient.getMemberUsedCouponHistoryByMemberId(pageable.getPageNumber(), pageable.getPageSize())
			.toPage();
	}

	@Override
	public String downloadCoupon(DownloadCouponRequest request) {
		try {
			memberCouponClient.downloadCoupon(request);
			return "쿠폰 발급에 성공했습니다 \uD83D\uDCE6";
		} catch (FeignClientException exception) {
			return exception.getMessage();
		}
	}

	@Override
	public void downloadLimitedCoupon(@Valid DownloadCouponMessageRequest request) {
		memberCouponClient.createMemberCouponByMessage(request);
	}

}

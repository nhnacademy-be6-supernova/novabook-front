package store.novabook.front.api.member.member.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.coupon.dto.request.CreateMemberCouponRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponHistoryResponse;
import store.novabook.front.api.coupon.dto.response.GetUsedCouponHistoryResponse;
import store.novabook.front.api.member.coupon.service.MemberCouponClient;
import store.novabook.front.api.member.member.dto.response.CreateMemberCouponResponse;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.api.member.member.service.MemberCouponService;
import store.novabook.front.common.response.ApiResponse;

@Service
@RequiredArgsConstructor
public class MemberCouponServiceImpl implements MemberCouponService {

	private final MemberClient memberClient;
	private final MemberCouponClient memberCouponClient;

	@Override
	public CreateMemberCouponResponse downloadCoupon(CreateMemberCouponRequest request) {
		return memberClient.createMemberCoupon(request).getBody();
	}

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

}

package store.novabook.front.api.member.member.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.coupon.dto.request.CreateMemberCouponRequest;
import store.novabook.front.api.member.member.dto.response.CreateMemberCouponResponse;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.api.member.member.service.MemberCouponService;

@Service
@RequiredArgsConstructor
public class MemberCouponServiceImpl implements MemberCouponService {
	private final MemberClient memberClient;

	@Override
	public CreateMemberCouponResponse downloadCoupon(CreateMemberCouponRequest request) {
		return memberClient.createMemberCoupon(request).getBody();
	}
}

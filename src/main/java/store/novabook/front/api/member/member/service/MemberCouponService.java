package store.novabook.front.api.member.member.service;

import store.novabook.front.api.coupon.dto.request.CreateMemberCouponRequest;
import store.novabook.front.api.member.member.dto.response.CreateMemberCouponResponse;

public interface MemberCouponService {
	CreateMemberCouponResponse downloadCoupon(Long memberId, CreateMemberCouponRequest request);
}

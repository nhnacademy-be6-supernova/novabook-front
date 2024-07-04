package store.novabook.front.api.member.member.service;

import store.novabook.front.api.coupon.dto.request.CreateMemberCouponRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponHistoryAllResponse;
import store.novabook.front.api.coupon.dto.response.GetUsedCouponHistoryAllResponse;
import store.novabook.front.api.member.member.dto.response.CreateMemberCouponResponse;

public interface MemberCouponService {
	CreateMemberCouponResponse downloadCoupon(CreateMemberCouponRequest request);

	GetCouponAllResponse getMyCouponAllWithValid();

	GetCouponHistoryAllResponse getMyCouponHistoryAll(int page, int size);

	GetUsedCouponHistoryAllResponse getMyUsedCouponHistory(int usagePage, int size);
}

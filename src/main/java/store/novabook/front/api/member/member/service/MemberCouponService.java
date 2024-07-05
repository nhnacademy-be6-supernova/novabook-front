package store.novabook.front.api.member.member.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import store.novabook.front.api.coupon.dto.request.CreateMemberCouponRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponHistoryResponse;
import store.novabook.front.api.coupon.dto.response.GetUsedCouponHistoryResponse;
import store.novabook.front.api.member.member.dto.response.CreateMemberCouponResponse;

public interface MemberCouponService {
	CreateMemberCouponResponse downloadCoupon(CreateMemberCouponRequest request);

	GetCouponAllResponse getMyCouponAllWithValid();

	Page<GetCouponHistoryResponse> getMyCouponHistoryAll(Pageable pageable);

	Page<GetUsedCouponHistoryResponse> getMyUsedCouponHistory(Pageable pageable);
}

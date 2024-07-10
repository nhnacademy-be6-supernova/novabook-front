package store.novabook.front.api.member.member.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;
import store.novabook.front.api.coupon.dto.request.DownloadCouponRequest;
import store.novabook.front.api.coupon.dto.response.GetCouponAllResponse;
import store.novabook.front.api.coupon.dto.response.GetCouponHistoryResponse;
import store.novabook.front.api.coupon.dto.response.GetUsedCouponHistoryResponse;
import store.novabook.front.api.member.coupon.dto.DownloadCouponMessageRequest;

public interface MemberCouponService {
	void downloadLimitedCoupon(@Valid DownloadCouponMessageRequest request);

	GetCouponAllResponse getMyCouponAllWithValid();

	Page<GetCouponHistoryResponse> getMyCouponHistoryAll(Pageable pageable);

	Page<GetUsedCouponHistoryResponse> getMyUsedCouponHistory(Pageable pageable);

	String downloadCoupon(DownloadCouponRequest request);
}

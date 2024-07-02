package store.novabook.front.store.member;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.coupon.dto.request.CreateMemberCouponRequest;
import store.novabook.front.api.member.member.service.MemberCouponService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class MemberCouponController {

	private final MemberCouponService memberCouponService;

	// 컨트롤러 메소드
	@PostMapping("/download")
	public void downloadCoupon(@Valid @RequestBody CreateMemberCouponRequest request) {
		// 임시로 설정한 회원 ID
		Long memberId = 3L;
		memberCouponService.downloadCoupon(memberId, request);
	}
}

package store.novabook.front.messaging;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.messaging.dto.DownloadCouponMessageRequest;
import store.novabook.front.store.member.MemberCouponController;

@RestController
@RequiredArgsConstructor
public class CouponMessageController {

	private final CouponSender couponSender;
	private final MemberCouponController memberCouponController;

	@PostMapping("/coupons/message/download/notify")
	public void downloadCoupon(@Valid @RequestBody DownloadCouponMessageRequest message) {
		memberCouponController.downloadLimited(message);
		couponSender.sendToHighTrafficCouponCreateQueue(message);
	}
}

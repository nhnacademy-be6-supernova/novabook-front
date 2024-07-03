package store.novabook.front.messaging;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import store.novabook.front.messaging.dto.CreateCouponMessage;

@RestController
@RequiredArgsConstructor
public class CouponMessageController {

	private final CouponSender couponSender;

	@PostMapping("/coupons/message/download")
	public void downloadCoupon(@Valid @RequestBody CreateCouponMessage message) {
		couponSender.sendToNormalCouponCreateQueue(message);
	}
}

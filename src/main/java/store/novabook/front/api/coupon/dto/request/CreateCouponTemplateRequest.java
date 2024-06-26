package store.novabook.front.api.coupon.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.domain.DiscountType;

public record CreateCouponTemplateRequest(
	@NotBlank(message = "이름은 필수 입력 항목입니다.") @Size(max = 255, message = "이름은 255자 이하로 입력해야 합니다.") String name,
	@NotNull(message = "쿠폰 타입은 필수 입력 항목입니다.") CouponType type,
	@NotNull(message = "할인 금액은 필수 입력 항목입니다.") @Min(value = 0, message = "할인 금액은 0 이상이어야 합니다.") Long discountAmount,
	@NotNull(message = "할인 유형은 필수 입력 항목입니다.") DiscountType discountType,
	@NotNull(message = "최대 할인 금액은 필수 입력 항목입니다.") @Min(value = 0, message = "최대 할인 금액은 0 이상이어야 합니다.")
	Long maxDiscountAmount,
	@NotNull(message = "최소 구매 금액은 필수 입력 항목입니다.") @Min(value = 0, message = "최소 구매 금액은 0 이상이어야 합니다.")
	Long minPurchaseAmount,
	@NotNull(message = "쿠폰 정책 시작 날짜는 필수 입력 항목입니다.") LocalDateTime startedAt,
	@NotNull(message = "쿠폰 정책 만료 날짜는 필수 입력 항목입니다.") LocalDateTime expirationAt,
	@NotNull(message = "사용 가능 기간은 필수 입력 항목입니다.") Integer usePeriod) {
}
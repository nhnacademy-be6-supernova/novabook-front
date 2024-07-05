package store.novabook.front.store.order.dto;

import java.util.List;

import lombok.Builder;
import store.novabook.front.api.coupon.dto.response.GetCouponResponse;
import store.novabook.front.api.delivery.dto.response.GetDeliveryFeeResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressResponse;
import store.novabook.front.api.order.dto.response.GetWrappingPaperResponse;

@Builder
public record OrderViewDTO(
	Boolean isPackable,
	List<GetWrappingPaperResponse> wrappingPapers,
	List<GetCouponResponse> coupons,
	List<GetMemberAddressResponse> memberAddresses,
	List<String> dates,
	long myPoint,
	GetDeliveryFeeResponse deliveryFeeInfo
) {
}
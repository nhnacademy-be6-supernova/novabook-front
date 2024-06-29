package store.novabook.front.api.coupon.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CouponType {
	GENERAL,
	BIRTHDAY,
	WELCOME,
	BOOK,
	CATEGORY;

	@JsonCreator
	public static CouponType forValue(String value) {
		return CouponType.valueOf(value.toUpperCase());
	}






}
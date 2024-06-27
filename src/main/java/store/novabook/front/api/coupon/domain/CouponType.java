package store.novabook.front.api.coupon.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

	@JsonValue
	public String toValue() {
		return this.name().toLowerCase();
	}
}
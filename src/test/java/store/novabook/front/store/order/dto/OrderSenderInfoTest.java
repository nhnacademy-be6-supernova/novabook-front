package store.novabook.front.store.order.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class OrderSenderInfoTest {

	private final Validator validator;

	public OrderSenderInfoTest() {
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			this.validator = factory.getValidator();
		}
	}

	@Test
	void testOrderSenderInfoValid() {
		OrderSenderInfo orderSenderInfo = new OrderSenderInfo(
			"Alice",
			"01012345678"
		);

		var violations = validator.validate(orderSenderInfo);

		assertThat(violations).isEmpty();
	}

	@Test
	void testOrderSenderInfoInvalidPhone() {
		OrderSenderInfo invalidPhoneInfo = new OrderSenderInfo(
			"Alice",
			"12345"
		);

		var violations = validator.validate(invalidPhoneInfo);

		assertThat(violations).anyMatch(v -> v.getMessage().contains("핸드폰 양식에 맞지 않습니다."));
	}

	@Test
	void testOrderSenderInfoInvalidName() {
		OrderSenderInfo invalidNameInfo = new OrderSenderInfo(
			"",
			"01012345678"
		);

		var violations = validator.validate(invalidNameInfo);

		assertThat(violations).anyMatch(v -> v.getMessage().contains("이름을 최소 한 글자 최대 100글자 내로 입력해주세요."));
	}
}

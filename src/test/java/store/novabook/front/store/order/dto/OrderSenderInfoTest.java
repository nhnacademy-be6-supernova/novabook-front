package store.novabook.front.store.order.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class OrderSenderInfoTest {

	private final Validator validator;

	public OrderSenderInfoTest() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}

	@Test
	public void testOrderSenderInfoValid() {
		// Arrange
		OrderSenderInfo orderSenderInfo = new OrderSenderInfo(
			"Alice",
			"01012345678"
		);

		// Act
		var violations = validator.validate(orderSenderInfo);

		// Assert
		assertThat(violations).isEmpty();
	}

	@Test
	public void testOrderSenderInfoInvalidPhone() {
		// Arrange
		OrderSenderInfo invalidPhoneInfo = new OrderSenderInfo(
			"Alice",
			"12345" // Invalid phone format
		);

		// Act
		var violations = validator.validate(invalidPhoneInfo);

		// Assert
		assertThat(violations).anyMatch(v -> v.getMessage().contains("핸드폰 양식에 맞지 않습니다."));
	}

	@Test
	public void testOrderSenderInfoInvalidName() {
		// Arrange
		OrderSenderInfo invalidNameInfo = new OrderSenderInfo(
			"", // Invalid name (should not be blank)
			"01012345678"
		);

		// Act
		var violations = validator.validate(invalidNameInfo);

		// Assert
		assertThat(violations).anyMatch(v -> v.getMessage().contains("이름을 최소 한 글자 최대 100글자 내로 입력해주세요."));
	}
}

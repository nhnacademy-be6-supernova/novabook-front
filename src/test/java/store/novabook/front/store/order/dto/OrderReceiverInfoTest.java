package store.novabook.front.store.order.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class OrderReceiverInfoTest {

	private final Validator validator;

	public OrderReceiverInfoTest() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}

	@Test
	public void testOrderReceiverInfoValid() {
		// Arrange
		OrderAddressInfo addressInfo = OrderAddressInfo.builder()
			.zipCode("12345")
			.streetAddress("123 Main St")
			.detailAddress("Apt 4B")
			.build();

		OrderReceiverInfo orderReceiverInfo = new OrderReceiverInfo(
			addressInfo,
			"John Doe",
			"01012345678",
			"john.doe@example.com"
		);

		// Act
		var violations = validator.validate(orderReceiverInfo);

		// Assert
		assertThat(violations).isEmpty();
	}

}

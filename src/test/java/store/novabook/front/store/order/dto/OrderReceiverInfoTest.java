package store.novabook.front.store.order.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class OrderReceiverInfoTest {

	private final Validator validator;

	public OrderReceiverInfoTest() {
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			this.validator = factory.getValidator();
		}
	}

	@Test
	void testOrderReceiverInfoValid() {
		OrderAddressInfo addressInfo = OrderAddressInfo.builder()
			.zipCode("12345")
			.streetAddresses("123 Main St")
			.detailAddress("Apt 4B")
			.build();

		OrderReceiverInfo orderReceiverInfo = new OrderReceiverInfo(
			addressInfo,
			"John Doe",
			"01012345678",
			"john.doe@example.com"
		);

		var violations = validator.validate(orderReceiverInfo);

		assertThat(violations).isEmpty();
	}

}

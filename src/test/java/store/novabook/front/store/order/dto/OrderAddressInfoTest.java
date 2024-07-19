package store.novabook.front.store.order.dto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class OrderAddressInfoTest {

	private final Validator validator;

	public OrderAddressInfoTest() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}

	@Test
	public void testOrderAddressInfoValid() {
		// Arrange
		String zipCode = "12345";
		String streetAddress = "123 Main St";
		String detailAddress = "Apt 4B";

		// Act
		OrderAddressInfo orderAddressInfo = OrderAddressInfo.builder()
			.zipCode(zipCode)
			.streetAddress(streetAddress)
			.detailAddress(detailAddress)
			.build();

		// Assert
		assertThat(orderAddressInfo).isNotNull();
		assertThat(orderAddressInfo.zipCode()).isEqualTo(zipCode);
		assertThat(orderAddressInfo.streetAddress()).isEqualTo(streetAddress);
		assertThat(orderAddressInfo.detailAddress()).isEqualTo(detailAddress);
	}

}

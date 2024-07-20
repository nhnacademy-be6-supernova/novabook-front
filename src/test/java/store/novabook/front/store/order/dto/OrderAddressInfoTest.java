package store.novabook.front.store.order.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderAddressInfoTest {


	@Test
	void testOrderAddressInfoValid() {
		String zipCode = "12345";
		String streetAddresses = "123 Main St";
		String detailAddress = "Apt 4B";

		OrderAddressInfo orderAddressInfo = OrderAddressInfo.builder()
			.zipCode(zipCode)
			.streetAddresses(streetAddresses)
			.detailAddress(detailAddress)
			.build();

		assertThat(orderAddressInfo).isNotNull();
		assertThat(orderAddressInfo.zipCode()).isEqualTo(zipCode);
		assertThat(orderAddressInfo.streetAddresses()).isEqualTo(streetAddresses);
		assertThat(orderAddressInfo.detailAddress()).isEqualTo(detailAddress);
	}

}

package store.novabook.front.api.member.address.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;


class StreetAddressTest {

	@Test
	void testBuilder() {
		Long id = 1L;
		String zipcode = "12345";
		String streetAddresses = "123 Main St";
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

		StreetAddress streetAddressesObj = StreetAddress.builder()
			.id(id)
			.zipcode(zipcode)
			.streetAddresses(streetAddresses)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.build();

		assertThat(streetAddressesObj).isNotNull();
		assertThat(streetAddressesObj.getId()).isEqualTo(id);
		assertThat(streetAddressesObj.getZipcode()).isEqualTo(zipcode);
		assertThat(streetAddressesObj.getStreetAddresses()).isEqualTo(streetAddresses);
		assertThat(streetAddressesObj.getCreatedAt()).isEqualTo(createdAt);
		assertThat(streetAddressesObj.getUpdatedAt()).isEqualTo(updatedAt);
	}

	@Test
	void testBuilderWithDefaults() {
		StreetAddress streetAddressesObj = StreetAddress.builder()
			.build();

		assertThat(streetAddressesObj).isNotNull();
		assertThat(streetAddressesObj.getId()).isNull();
		assertThat(streetAddressesObj.getZipcode()).isNull();
		assertThat(streetAddressesObj.getStreetAddresses()).isNull();
		assertThat(streetAddressesObj.getCreatedAt()).isNull();
		assertThat(streetAddressesObj.getUpdatedAt()).isNull();
	}
}

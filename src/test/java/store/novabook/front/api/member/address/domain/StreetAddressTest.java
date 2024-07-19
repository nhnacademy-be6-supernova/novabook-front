package store.novabook.front.api.member.address.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;


class StreetAddressTest {

	@Test
	void testBuilder() {
		// Arrange
		Long id = 1L;
		String zipcode = "12345";
		String streetAddress = "123 Main St";
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

		// Act
		StreetAddress streetAddressObj = StreetAddress.builder()
			.id(id)
			.zipcode(zipcode)
			.streetAddress(streetAddress)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.build();

		// Assert
		assertThat(streetAddressObj).isNotNull();
		assertThat(streetAddressObj.getId()).isEqualTo(id);
		assertThat(streetAddressObj.getZipcode()).isEqualTo(zipcode);
		assertThat(streetAddressObj.getStreetAddress()).isEqualTo(streetAddress);
		assertThat(streetAddressObj.getCreatedAt()).isEqualTo(createdAt);
		assertThat(streetAddressObj.getUpdatedAt()).isEqualTo(updatedAt);
	}

	@Test
	void testBuilderWithDefaults() {
		// Act
		StreetAddress streetAddressObj = StreetAddress.builder()
			.build();

		// Assert
		assertThat(streetAddressObj).isNotNull();
		assertThat(streetAddressObj.getId()).isNull();
		assertThat(streetAddressObj.getZipcode()).isNull();
		assertThat(streetAddressObj.getStreetAddress()).isNull();
		assertThat(streetAddressObj.getCreatedAt()).isNull();
		assertThat(streetAddressObj.getUpdatedAt()).isNull();
	}
}

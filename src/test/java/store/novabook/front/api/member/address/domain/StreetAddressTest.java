package store.novabook.front.api.member.address.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;


class StreetAddressTest {

	@Test
	void testBuilder() {
		Long id = 1L;
		String zipcode = "12345";
		String streetAddress = "123 Main St";
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

		StreetAddress streetAddressObj = StreetAddress.builder()
			.id(id)
			.zipcode(zipcode)
			.streetAddress(streetAddress)
			.createdAt(createdAt)
			.updatedAt(updatedAt)
			.build();

		assertThat(streetAddressObj).isNotNull();
		assertThat(streetAddressObj.getId()).isEqualTo(id);
		assertThat(streetAddressObj.getZipcode()).isEqualTo(zipcode);
		assertThat(streetAddressObj.getStreetAddress()).isEqualTo(streetAddress);
		assertThat(streetAddressObj.getCreatedAt()).isEqualTo(createdAt);
		assertThat(streetAddressObj.getUpdatedAt()).isEqualTo(updatedAt);
	}

	@Test
	void testBuilderWithDefaults() {
		StreetAddress streetAddressObj = StreetAddress.builder()
			.build();

		assertThat(streetAddressObj).isNotNull();
		assertThat(streetAddressObj.getId()).isNull();
		assertThat(streetAddressObj.getZipcode()).isNull();
		assertThat(streetAddressObj.getStreetAddress()).isNull();
		assertThat(streetAddressObj.getCreatedAt()).isNull();
		assertThat(streetAddressObj.getUpdatedAt()).isNull();
	}
}

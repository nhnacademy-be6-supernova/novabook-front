package store.novabook.front.api.member.member.dto.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DuplicateResponseTest {

	@Test
	void testDuplicateResponse_Builder() {
		Boolean expectedIsDuplicate = true;

		DuplicateResponse response = DuplicateResponse.builder()
			.isDuplicate(expectedIsDuplicate)
			.build();

		assertThat(response).isNotNull();
		assertThat(response.isDuplicate()).isEqualTo(expectedIsDuplicate);
	}

	@Test
	void testDuplicateResponse_NullIsDuplicate() {
		Boolean expectedIsDuplicate = null;

		DuplicateResponse response = DuplicateResponse.builder()
			.isDuplicate(expectedIsDuplicate)
			.build();

		assertThat(response).isNotNull();
		assertThat(response.isDuplicate()).isNull();
	}
}

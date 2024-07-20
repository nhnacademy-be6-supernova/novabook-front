package store.novabook.front.api.member.member.dto.request;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import store.novabook.front.api.member.address.dto.request.UpdateMemberAddressRequest;

class UpdateMemberAddressRequestTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void testUpdateMemberAddressRequestBuilder_Success() {
		UpdateMemberAddressRequest request = UpdateMemberAddressRequest.builder()
			.nickname("Home")
			.zipcode("12345")
			.streetAddress("123 Main St")
			.memberAddressDetail("Apt 4B")
			.build();

		Set<ConstraintViolation<UpdateMemberAddressRequest>> violations = validator.validate(request);

		assertThat(violations).isEmpty();
	}

	@Test
	void testUpdateMemberAddressRequestBuilder_Failure() {
		UpdateMemberAddressRequest request = UpdateMemberAddressRequest.builder()
			.nickname("")
			.zipcode("")
			.streetAddress("")
			.memberAddressDetail("")
			.build();

		Set<ConstraintViolation<UpdateMemberAddressRequest>> violations = validator.validate(request);

		assertThat(violations).hasSize(4);
	}

	@Test
	void testUpdateMemberAddressRequestBuilder_NullValues() {
		UpdateMemberAddressRequest request = UpdateMemberAddressRequest.builder()
			.nickname(null)
			.zipcode(null)
			.streetAddress(null)
			.memberAddressDetail(null)
			.build();

		Set<ConstraintViolation<UpdateMemberAddressRequest>> violations = validator.validate(request);

		assertThat(violations).hasSize(4);
	}
}

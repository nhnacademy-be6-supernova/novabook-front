package store.novabook.front.api.member.member.dto.request;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import store.novabook.front.api.member.address.dto.request.CreateMemberAddressRequest;

class CreateMemberAddressRequestTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void testCreateMemberAddressRequestBuilder_Success() {
		CreateMemberAddressRequest request = CreateMemberAddressRequest.builder()
			.zipcode("12345")
			.streetAddresses("123 Main St")
			.nickname("Home")
			.memberAddressDetail("Apt 4B")
			.build();

		Set<ConstraintViolation<CreateMemberAddressRequest>> violations = validator.validate(request);

		assertThat(violations).isEmpty();
	}

	@Test
	void testCreateMemberAddressRequestBuilder_Failure() {
		CreateMemberAddressRequest request = CreateMemberAddressRequest.builder()
			.zipcode("")
			.streetAddresses("")
			.nickname("")
			.memberAddressDetail("")
			.build();

		Set<ConstraintViolation<CreateMemberAddressRequest>> violations = validator.validate(request);

		assertThat(violations).hasSize(4);
	}

	@Test
	void testCreateMemberAddressRequestBuilder_NullValues() {
		CreateMemberAddressRequest request = CreateMemberAddressRequest.builder()
			.zipcode(null)
			.streetAddresses(null)
			.nickname(null)
			.memberAddressDetail(null)
			.build();

		Set<ConstraintViolation<CreateMemberAddressRequest>> violations = validator.validate(request);

		assertThat(violations).hasSize(4);
	}

}

package store.novabook.front.api.member.member.dto.request;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class UpdateMemberRequestTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void testUpdateMemberRequest_Valid() {
		UpdateMemberRequest request = new UpdateMemberRequest("John Doe", "01234567890");

		Set<ConstraintViolation<UpdateMemberRequest>> violations = validator.validate(request);

		assertThat(violations).isEmpty();
	}

	@Test
	void testUpdateMemberRequest_InvalidName() {
		String longName = "A".repeat(51);
		UpdateMemberRequest request = new UpdateMemberRequest(longName, "01234567890");

		Set<ConstraintViolation<UpdateMemberRequest>> violations = validator.validate(request);

		assertThat(violations).anyMatch(violation -> violation.getMessage().equals("최대 50자까지 가능합니다."));
	}


	@Test
	void testUpdateMemberRequest_NullValues() {
		UpdateMemberRequest request = new UpdateMemberRequest(null, null);

		Set<ConstraintViolation<UpdateMemberRequest>> violations = validator.validate(request);

		assertThat(violations).isEmpty();
	}
}

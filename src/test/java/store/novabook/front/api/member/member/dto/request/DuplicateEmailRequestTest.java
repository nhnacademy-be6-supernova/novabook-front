package store.novabook.front.api.member.member.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DuplicateEmailRequestTest {

	private static Validator validator;

	@BeforeAll
	static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void testDuplicateEmailRequest_Builder() {
		String expectedEmail = "test@example.com";

		DuplicateEmailRequest request = DuplicateEmailRequest.builder()
			.email(expectedEmail)
			.build();

		assertThat(request).isNotNull();
		assertThat(request.email()).isEqualTo(expectedEmail);
	}

	@Test
	void testDuplicateEmailRequest_NullEmail() {
		String nullEmail = null;

		DuplicateEmailRequest request = DuplicateEmailRequest.builder()
			.email(nullEmail)
			.build();

		Set<ConstraintViolation<DuplicateEmailRequest>> violations = validator.validate(request);
		assertThat(violations).isNotEmpty();
		assertThat(violations.iterator().next().getMessage()).isEqualTo("이메일은 필수 입력값입니다.");
	}

	@Test
	void testDuplicateEmailRequest_BlankEmail() {
		String blankEmail = "";

		DuplicateEmailRequest request = DuplicateEmailRequest.builder()
			.email(blankEmail)
			.build();

		Set<ConstraintViolation<DuplicateEmailRequest>> violations = validator.validate(request);
		assertThat(violations).isNotEmpty();
		assertThat(violations.iterator().next().getMessage()).isEqualTo("이메일은 필수 입력값입니다.");
	}
}

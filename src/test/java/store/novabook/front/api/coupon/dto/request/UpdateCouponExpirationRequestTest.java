package store.novabook.front.api.coupon.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateCouponExpirationRequestTest {

	private static Validator validator;

	@BeforeAll
	static void setUpValidator() {
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			validator = factory.getValidator();
		}
	}

	@Test
	void testUpdateCouponExpirationRequest_Valid() {
		UpdateCouponExpirationRequest request = new UpdateCouponExpirationRequest(123L);

		Set<ConstraintViolation<UpdateCouponExpirationRequest>> violations = validator.validate(request);
		assertEquals(0, violations.size());
	}

	@Test
	void testUpdateCouponExpirationRequest_Invalid() {
		UpdateCouponExpirationRequest request = new UpdateCouponExpirationRequest(null);

		Set<ConstraintViolation<UpdateCouponExpirationRequest>> violations = validator.validate(request);
		assertEquals(0, violations.size()); // Since there are no validation annotations, there should be no violations
	}
}

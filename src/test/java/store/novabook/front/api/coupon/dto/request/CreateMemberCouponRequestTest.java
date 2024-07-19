package store.novabook.front.api.coupon.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class CreateMemberCouponRequestTest {

	private static Validator validator;

	@BeforeAll
	public static void setUpValidator() {
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			validator = factory.getValidator();
		}
	}

	@Test
	void testCreateMemberCouponRequest_Valid() {
		CreateMemberCouponRequest request = CreateMemberCouponRequest.builder()
			.couponTemplateId(1L)
			.build();

		Set<ConstraintViolation<CreateMemberCouponRequest>> violations = validator.validate(request);
		assertEquals(0, violations.size());
	}

	@Test
	void testCreateMemberCouponRequest_Invalid() {
		CreateMemberCouponRequest request = CreateMemberCouponRequest.builder()
			.couponTemplateId(null)
			.build();

		Set<ConstraintViolation<CreateMemberCouponRequest>> violations = validator.validate(request);
		assertEquals(1, violations.size());
		ConstraintViolation<CreateMemberCouponRequest> violation = violations.iterator().next();
		assertEquals("쿠폰 템플릿 ID는 필수 입력 항목입니다.", violation.getMessage());
		assertEquals("couponTemplateId", violation.getPropertyPath().toString());
	}
}

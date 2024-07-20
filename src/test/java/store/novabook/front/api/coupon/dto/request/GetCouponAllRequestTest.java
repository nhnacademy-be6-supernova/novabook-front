package store.novabook.front.api.coupon.dto.request;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class GetCouponAllRequestTest {

	private Validator validator;

	@BeforeEach
	public void setUp() {
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			validator = factory.getValidator();
		}
	}

	@Test
	void testValidGetCouponAllRequest() {
		GetCouponAllRequest request = GetCouponAllRequest.builder()
			.couponIdList(List.of(1L, 2L, 3L))
			.bookIdList(Set.of(4L, 5L))
			.categoryIdList(Set.of(6L, 7L))
			.build();

		Set<ConstraintViolation<GetCouponAllRequest>> violations = validator.validate(request);
		assertTrue(violations.isEmpty(), "There should be no constraint violations");
	}

	@Test
	void testNullCouponIdList() {
		GetCouponAllRequest request = GetCouponAllRequest.builder()
			.bookIdList(Set.of(4L, 5L))
			.categoryIdList(Set.of(6L, 7L))
			.build();

		Set<ConstraintViolation<GetCouponAllRequest>> violations = validator.validate(request);
		assertEquals(1, violations.size());

		ConstraintViolation<GetCouponAllRequest> violation = violations.iterator().next();
		assertEquals("쿠폰 번호는 필수 입력 항목입니다.", violation.getMessage());
		assertEquals("couponIdList", violation.getPropertyPath().toString());
	}

	@Test
	void testNullBookIdList() {
		GetCouponAllRequest request = GetCouponAllRequest.builder()
			.couponIdList(List.of(1L, 2L, 3L))
			.categoryIdList(Set.of(6L, 7L))
			.build();

		Set<ConstraintViolation<GetCouponAllRequest>> violations = validator.validate(request);
		assertEquals(1, violations.size());

		ConstraintViolation<GetCouponAllRequest> violation = violations.iterator().next();
		assertEquals("도서 아이디는 필수 입력 항목입니다.", violation.getMessage());
		assertEquals("bookIdList", violation.getPropertyPath().toString());
	}

	@Test
	void testNullCategoryIdList() {
		GetCouponAllRequest request = GetCouponAllRequest.builder()
			.couponIdList(List.of(1L, 2L, 3L))
			.bookIdList(Set.of(4L, 5L))
			.build();

		Set<ConstraintViolation<GetCouponAllRequest>> violations = validator.validate(request);
		assertEquals(1, violations.size());

		ConstraintViolation<GetCouponAllRequest> violation = violations.iterator().next();
		assertEquals("카테고리 아이디는 필수 입력 항목입니다.", violation.getMessage());
		assertEquals("categoryIdList", violation.getPropertyPath().toString());
	}

	@Test
	void testAllNullFields() {
		GetCouponAllRequest request = GetCouponAllRequest.builder().build();

		Set<ConstraintViolation<GetCouponAllRequest>> violations = validator.validate(request);
		assertEquals(3, violations.size());

		Set<String> messages = violations.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toSet());

		assertTrue(messages.contains("쿠폰 번호는 필수 입력 항목입니다."));
		assertTrue(messages.contains("도서 아이디는 필수 입력 항목입니다."));
		assertTrue(messages.contains("카테고리 아이디는 필수 입력 항목입니다."));
	}
}

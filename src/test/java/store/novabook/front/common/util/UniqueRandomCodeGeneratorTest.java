package store.novabook.front.common.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UniqueRandomCodeGeneratorTest {

	@Mock
	private RedisTemplate<String, Object> redisTemplate;

	@Mock
	private SetOperations<String, Object> setOperations;

	private UniqueRandomCodeGenerator uniqueRandomCodeGenerator;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(redisTemplate.opsForSet()).thenReturn(setOperations);
		uniqueRandomCodeGenerator = new UniqueRandomCodeGenerator(redisTemplate);
	}

	@Test
	void generateUniqueRandomCode_returnsUniqueCode() {
		String expectedDatePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

		// Set up mocks
		when(setOperations.isMember(anyString(), anyString())).thenReturn(false);

		// Generate unique random code
		String code = uniqueRandomCodeGenerator.generateUniqueRandomCode();

		// Validate code format and parts
		assertEquals(11, code.length());
		assertEquals(expectedDatePart, code.substring(0, 6));

		// Capture arguments and verify interactions
		ArgumentCaptor<String> setKeyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> setValueCaptor = ArgumentCaptor.forClass(String.class);

		verify(setOperations).isMember(setKeyCaptor.capture(), setValueCaptor.capture());
		verify(setOperations).add(eq("uniqueCodes"), anyString());

		assertEquals("uniqueCodes", setKeyCaptor.getValue());
		// You may also want to check that the value matches what was generated
	}

	@Test
	void generateUniqueRandomCode_handlesCollision() {
		String expectedDatePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

		// Set up mocks
		when(setOperations.isMember(anyString(), anyString()))
			.thenReturn(true) // First code is in Redis
			.thenReturn(false); // Second code is not in Redis

		// Generate unique random code
		String code = uniqueRandomCodeGenerator.generateUniqueRandomCode();

		// Validate code format and parts
		assertEquals(11, code.length());
		assertEquals(expectedDatePart, code.substring(0, 6));

		// Capture arguments and verify interactions
		ArgumentCaptor<String> setKeyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> setValueCaptor = ArgumentCaptor.forClass(String.class);

		verify(setOperations, times(2)).isMember(setKeyCaptor.capture(), setValueCaptor.capture());
		verify(setOperations).add(eq("uniqueCodes"), anyString());

		// Check that isMember was called with correct key
		assertEquals("uniqueCodes", setKeyCaptor.getValue());
	}
}

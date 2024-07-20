package store.novabook.front.common.response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.NovaException;

class ErrorResponseTest {

	@Test
	void testFromNovaException() {
		ErrorCode errorCode = ErrorCode.INVALID_REQUEST_ARGUMENT;
		NovaException novaException = mock(NovaException.class);
		when(novaException.getErrorCode()).thenReturn(errorCode);

		ErrorResponse errorResponse = ErrorResponse.from(novaException);

		assertEquals(errorCode, errorResponse.errorCode());
		assertEquals(errorCode.getMessage(), errorResponse.message());
	}

	@Test
	void testFromErrorCode() {
		ErrorCode errorCode = ErrorCode.INVALID_REQUEST_ARGUMENT;

		ErrorResponse errorResponse = ErrorResponse.from(errorCode);

		assertEquals(errorCode, errorResponse.errorCode());
		assertEquals(errorCode.getMessage(), errorResponse.message());
	}
}

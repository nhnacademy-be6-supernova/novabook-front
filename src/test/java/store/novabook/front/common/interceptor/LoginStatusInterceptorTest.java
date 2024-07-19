package store.novabook.front.common.interceptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import store.novabook.front.common.exception.AlreadyLoginException;
import store.novabook.front.common.exception.ErrorCode;

public class LoginStatusInterceptorTest {

	private LoginStatusInterceptor loginStatusInterceptor;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		loginStatusInterceptor = new LoginStatusInterceptor();
	}

	@Test
	void preHandle_WithAuthorizationCookie_ShouldThrowException() throws Exception {
		// Arrange
		Cookie authorizationCookie = new Cookie("Authorization", "token");
		Cookie refreshCookie = new Cookie("Refresh", "refreshToken");
		Cookie[] cookies = new Cookie[]{authorizationCookie, refreshCookie};
		when(request.getCookies()).thenReturn(cookies);

		// Act & Assert
		AlreadyLoginException thrown = assertThrows(AlreadyLoginException.class, () -> {
			loginStatusInterceptor.preHandle(request, response, new Object());
		});
		assertEquals(ErrorCode.ALREADY_LOGIN, thrown.getErrorCode());


	}

	@Test
	void preHandle_NoAuthorizationCookie_ShouldProceed() throws Exception {
		// Arrange
		Cookie[] cookies = new Cookie[0];
		when(request.getCookies()).thenReturn(cookies);

		// Act
		boolean result = loginStatusInterceptor.preHandle(request, response, new Object());

		// Assert
		assertTrue(result);
	}

	@Test
	void preHandle_WithRefreshCookie_ShouldThrowException() throws Exception {
		// Arrange
		Cookie refreshCookie = new Cookie("Refresh", "refreshToken");
		Cookie[] cookies = new Cookie[]{refreshCookie};
		when(request.getCookies()).thenReturn(cookies);

		// Act & Assert
		AlreadyLoginException thrown = assertThrows(AlreadyLoginException.class, () -> {
			loginStatusInterceptor.preHandle(request, response, new Object());
		});
		assertEquals(ErrorCode.ALREADY_LOGIN, thrown.getErrorCode());

	}

	@Test
	void preHandle_WithAuthorizationAndRefreshCookies_ShouldThrowException() throws Exception {
		// Arrange
		Cookie authorizationCookie = new Cookie("Authorization", "token");
		Cookie refreshCookie = new Cookie("Refresh", "refreshToken");
		Cookie[] cookies = new Cookie[]{authorizationCookie, refreshCookie};
		when(request.getCookies()).thenReturn(cookies);

		// Act & Assert
		AlreadyLoginException thrown = assertThrows(AlreadyLoginException.class, () -> {
			loginStatusInterceptor.preHandle(request, response, new Object());
		});
		assertEquals(ErrorCode.ALREADY_LOGIN, thrown.getErrorCode());

	}
}

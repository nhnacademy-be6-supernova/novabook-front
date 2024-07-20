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

class LoginStatusInterceptorTest {

	private LoginStatusInterceptor loginStatusInterceptor;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		loginStatusInterceptor = new LoginStatusInterceptor();
	}

	@Test
	void testPreHandle_AlreadyLoginException() {
		Cookie authorizationCookie = new Cookie("Authorization", "token");
		Cookie refreshCookie = new Cookie("Refresh", "refreshToken");
		Cookie[] cookies = new Cookie[]{authorizationCookie, refreshCookie};
		when(request.getCookies()).thenReturn(cookies);

		AlreadyLoginException thrown = assertThrows(AlreadyLoginException.class, this::handleRequest);
		assertEquals(ErrorCode.ALREADY_LOGIN, thrown.getErrorCode());
	}

	private void handleRequest() {
		try {
			loginStatusInterceptor.preHandle(request, response, new Object());
		} catch (AlreadyLoginException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Test
	void preHandle_NoAuthorizationCookie_ShouldProceed() throws Exception {
		Cookie[] cookies = new Cookie[0];
		when(request.getCookies()).thenReturn(cookies);

		boolean result = loginStatusInterceptor.preHandle(request, response, new Object());

		assertTrue(result);
	}

	@Test
	void preHandle_WithRefreshCookie_ShouldThrowException() {
		Cookie refreshCookie = new Cookie("Refresh", "refreshToken");
		Cookie[] cookies = new Cookie[]{refreshCookie};
		when(request.getCookies()).thenReturn(cookies);

		AlreadyLoginException thrown = assertThrows(AlreadyLoginException.class, this::invokePreHandle);
		assertEquals(ErrorCode.ALREADY_LOGIN, thrown.getErrorCode());
	}

	private void invokePreHandle() {
		try {
			loginStatusInterceptor.preHandle(request, response, new Object());
		} catch (AlreadyLoginException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

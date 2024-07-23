package store.novabook.front.common.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

class CookieUtilTest {

	private HttpServletResponse response;

	@BeforeEach
	void setUp() {
		response = mock(HttpServletResponse.class);
	}

	@Test
	void constructor_throwsUnsupportedOperationException() {
		InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
			Constructor<CookieUtil> constructor = CookieUtil.class.getDeclaredConstructor();
			constructor.setAccessible(true); // private 생성자 접근 허용
			constructor.newInstance();
		});

		// Check the actual cause of the exception
		assertThrows(UnsupportedOperationException.class, () -> {
			throw exception.getCause();
		});
	}

	@Test
	void createMemberCookie() {
		Long cartId = 123L;

		CookieUtil.createMemberCookie(response, cartId);

		ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
		verify(response, times(1)).addCookie(cookieCaptor.capture());

		Cookie cookie = cookieCaptor.getValue();
		assertEquals(CookieUtil.MEMBER_COOKIE_NAME, cookie.getName());
		assertEquals(cartId.toString(), cookie.getValue());
		assertEquals("/", cookie.getPath());
		assertTrue(cookie.isHttpOnly());
		assertEquals(CookieUtil.COOKIE_MAX_AGE, cookie.getMaxAge());
	}

	@Test
	void createGuestCookie() {
		String cartId = "guestCartId";

		CookieUtil.createGuestCookie(response, cartId);

		ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
		verify(response, times(1)).addCookie(cookieCaptor.capture());

		Cookie cookie = cookieCaptor.getValue();
		assertEquals(CookieUtil.GUEST_COOKIE_NAME, cookie.getName());
		assertEquals(cartId, cookie.getValue());
		assertEquals("/", cookie.getPath());
		assertTrue(cookie.isHttpOnly());
		assertEquals(CookieUtil.COOKIE_MAX_AGE, cookie.getMaxAge());
	}

	@Test
	void deleteMemberCookie() {
		CookieUtil.deleteMemberCookie(response);

		ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
		verify(response, times(1)).addCookie(cookieCaptor.capture());

		Cookie cookie = cookieCaptor.getValue();
		assertEquals(CookieUtil.MEMBER_COOKIE_NAME, cookie.getName());
		assertNull(cookie.getValue());
		assertEquals("/", cookie.getPath());
		assertTrue(cookie.isHttpOnly());
		assertEquals(0, cookie.getMaxAge());
	}

	@Test
	void deleteAuthorizationCookie() {
		LoginCookieUtil.deleteAuthorizationCookie(response);

		ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
		verify(response, times(2)).addCookie(cookieCaptor.capture());

		Cookie cookie1 = cookieCaptor.getAllValues().getFirst();
		assertEquals("Authorization", cookie1.getName());
		assertNull(cookie1.getValue());
		assertEquals("/", cookie1.getPath());
		assertTrue(cookie1.isHttpOnly());
		assertEquals(0, cookie1.getMaxAge());

		Cookie cookie2 = cookieCaptor.getAllValues().get(1);
		assertEquals("Refresh", cookie2.getName());
		assertNull(cookie2.getValue());
		assertEquals("/", cookie2.getPath());
		assertTrue(cookie2.isHttpOnly());
		assertEquals(0, cookie2.getMaxAge());
	}

	@Test
	void deleteGuestCookie() {
		CookieUtil.deleteGuestCookie(response);

		ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
		verify(response, times(1)).addCookie(cookieCaptor.capture());

		Cookie cookie = cookieCaptor.getValue();
		assertEquals(CookieUtil.GUEST_COOKIE_NAME, cookie.getName());
		assertNull(cookie.getValue());
		assertEquals("/", cookie.getPath());
		assertTrue(cookie.isHttpOnly());
		assertEquals(0, cookie.getMaxAge());
	}
}

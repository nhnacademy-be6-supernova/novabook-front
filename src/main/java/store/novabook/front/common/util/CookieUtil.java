package store.novabook.front.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static final String MEMBER_COOKIE_NAME = "cart_member";
	public static final String GUEST_COOKIE_NAME = "cart_guest";
	public static final int COOKIE_MAX_AGE = 7 * 24 * 60 * 60; // 7 days

	public static void createMemberCookie(HttpServletResponse response, Long cartId) {
		createCookie(response, MEMBER_COOKIE_NAME, cartId.toString());
	}

	public static void createGuestCookie(HttpServletResponse response, String cartId) {
		createCookie(response, GUEST_COOKIE_NAME, cartId);
	}

	public static void deleteMemberCookie(HttpServletResponse response) {
		deleteCookie(response, MEMBER_COOKIE_NAME);
	}

	public static void deleteAuthorizationCookie(HttpServletResponse response) {
		deleteCookie(response, "Authorization");
		deleteCookie(response, "Refresh");
	}

	public static void deleteGuestCookie(HttpServletResponse response) {
		deleteCookie(response, GUEST_COOKIE_NAME);
	}

	private static void createCookie(HttpServletResponse response, String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(CookieUtil.COOKIE_MAX_AGE);
		response.addCookie(cookie);
	}

	private static void deleteCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}

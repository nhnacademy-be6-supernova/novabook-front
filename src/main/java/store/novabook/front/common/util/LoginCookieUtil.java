package store.novabook.front.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class LoginCookieUtil {

	private static final int ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 3;

	private LoginCookieUtil() {
		throw new UnsupportedOperationException("Utility class should not be instantiated");
	}

	public static void deleteAuthorizationCookie(HttpServletResponse response) {
		deleteLoginCookie(response, "Refresh");
		deleteLoginCookie(response, "Authorization");
	}

	public static void createAccessTokenCookie(HttpServletResponse response, String accessToken) {
		Cookie accessTokenCookie = new Cookie("Authorization", accessToken);
		accessTokenCookie.setPath("/");
		accessTokenCookie.setMaxAge(ACCESS_TOKEN_EXPIRE_TIME);
		accessTokenCookie.setHttpOnly(true);
		accessTokenCookie.setSecure(true);
		response.addCookie(accessTokenCookie);
	}

	private static void deleteLoginCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}

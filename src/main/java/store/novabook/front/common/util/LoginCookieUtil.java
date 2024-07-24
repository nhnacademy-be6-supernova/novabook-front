package store.novabook.front.common.util;

import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.HttpServletResponse;

public class LoginCookieUtil {

	private static final int ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 3;
	private static final int REFRESH_TOKEN_EXPIRE_TIME = 60 * 60 * 72;
	private static final String SAME_SITE = "Strict";
	private static final String SET_COOKIE = "Set-Cookie";

	private LoginCookieUtil() {
		throw new UnsupportedOperationException("Utility class should not be instantiated");
	}

	public static void deleteAuthorizationCookie(HttpServletResponse response) {
		deleteLoginCookie(response, "Refresh");
		deleteLoginCookie(response, "Authorization");
	}

	public static void createAccessTokenCookie(HttpServletResponse response, String accessToken) {
		ResponseCookie accessTokenCookie = ResponseCookie.from("Authorization", accessToken)
			.path("/")
			.maxAge(ACCESS_TOKEN_EXPIRE_TIME)
			.httpOnly(true)
			.secure(true)
			.sameSite(SAME_SITE)
			.build();
		response.addHeader(SET_COOKIE, accessTokenCookie.toString());

	}

	public static void createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
		ResponseCookie refreshTokenCookie = ResponseCookie.from("Refresh", refreshToken)
			.path("/")
			.maxAge(REFRESH_TOKEN_EXPIRE_TIME)
			.httpOnly(true)
			.secure(true)
			.sameSite(SAME_SITE)
			.build();

		response.addHeader(SET_COOKIE, refreshTokenCookie.toString());

	}

	private static void deleteLoginCookie(HttpServletResponse response, String name) {
		ResponseCookie cookie = ResponseCookie.from(name)
			.path("/")
			.maxAge(0)
			.httpOnly(true)
			.secure(true)
			.sameSite(SAME_SITE)
			.build();

		response.addHeader(SET_COOKIE, cookie.toString());

	}
}

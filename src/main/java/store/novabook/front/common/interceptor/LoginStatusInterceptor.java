package store.novabook.front.common.interceptor;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import store.novabook.front.common.exception.AlreadyLoginException;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.util.LoginCookieUtil;

public class LoginStatusInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response,
		@NotNull Object handler) throws Exception {
		Cookie[] cookies = request.getCookies();
		boolean hasAuthorization = false;
		boolean hasRefresh = false;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("Authorization".equals(cookie.getName())) {
					hasAuthorization = true;
				}
				if ("Refresh".equals(cookie.getName())) {
					hasRefresh = true;
				}
			}
		}

		if (hasAuthorization || hasRefresh) {
			LoginCookieUtil.deleteAuthorizationCookie(response);
			throw new AlreadyLoginException(ErrorCode.ALREADY_LOGIN);
		}

		return true;
	}
}

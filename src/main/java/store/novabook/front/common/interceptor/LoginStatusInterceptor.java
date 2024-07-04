package store.novabook.front.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import store.novabook.front.common.security.exception.AlreadyLoginException;

public class LoginStatusInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
			throw new AlreadyLoginException();
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// Do nothing
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// Do nothing
	}
}

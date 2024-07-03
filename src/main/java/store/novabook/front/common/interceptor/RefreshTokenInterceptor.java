package store.novabook.front.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.common.security.RefreshTokenContext;

@RequiredArgsConstructor
public class RefreshTokenInterceptor implements HandlerInterceptor {

	private final RefreshTokenContext refreshTokenContext;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		if (refreshTokenContext.getUri() == null) {
			refreshTokenContext.setUri(request.getRequestURI());
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {

		if (refreshTokenContext.getTokenData() == null) {
			return;
		}
		if (refreshTokenContext.getTokenData().equals("expired")) {
			refreshTokenContext.setTokenData(null);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
			return;
		}

		Cookie cookie = new Cookie("Authorization", refreshTokenContext.getTokenData());
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 7);
		response.addCookie(cookie);
		response.setHeader("access", refreshTokenContext.getTokenData());

		RequestDispatcher dispatcher = request.getRequestDispatcher(refreshTokenContext.getUri());
		dispatcher.forward(request, response);

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {
		if (refreshTokenContext.getTokenData().equals("expired")) {
			refreshTokenContext.setTokenData(null);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
			return;
		}

		if (refreshTokenContext.getUri() != null) {
			Cookie cookie = new Cookie("Authorization", refreshTokenContext.getTokenData());
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 7);
			response.addCookie(cookie);
			response.setHeader("access", refreshTokenContext.getTokenData());
			RequestDispatcher dispatcher = request.getRequestDispatcher(refreshTokenContext.getUri());
			dispatcher.forward(request, response);
		}
	}
}

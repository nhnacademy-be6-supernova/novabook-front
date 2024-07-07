package store.novabook.front.common.interceptor;

import java.util.Objects;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.common.security.RefreshTokenContext;
import store.novabook.front.common.security.exception.NotLoginException;

@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

	private final RefreshTokenContext refreshTokenContext;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		//지우지마시오(로그인관련)
		// Cookie[] cookies = request.getCookies();
		// boolean hasAuthorization = false;
		// boolean hasRefresh = false;
		//
		// if (cookies != null) {
		// 	for (Cookie cookie : cookies) {
		// 		if ("Authorization".equals(cookie.getName())) {
		// 			hasAuthorization = true;
		// 		}
		// 		if ("Refresh".equals(cookie.getName())) {
		// 			hasRefresh = true;
		// 		}
		// 	}
		// }
		//
		// if (!hasAuthorization || !hasRefresh) {
		// 	throw new NotLoginException();
		// }

		if (refreshTokenContext.getUri() == null && !request.getRequestURI().equals("/error")
			&& !request.getRequestURI().equals("/admin")) {
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
		response.setHeader("refresh", refreshTokenContext.getRefreshToken());
		RequestDispatcher dispatcher = request.getRequestDispatcher(refreshTokenContext.getUri());
		refreshTokenContext.setUri(null);
		dispatcher.forward(request, response);

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {

		if (Objects.nonNull(refreshTokenContext.getTokenData()) && refreshTokenContext.getTokenData()
			.equals("expired")) {
			refreshTokenContext.setTokenData(null);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
			return;
		}

		if (Objects.nonNull(refreshTokenContext.getTokenData()) && refreshTokenContext.getUri() != null) {
			Cookie cookie = new Cookie("Authorization", refreshTokenContext.getTokenData());
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 7);
			response.addCookie(cookie);
			response.setHeader("access", refreshTokenContext.getTokenData());
			response.setHeader("refresh", refreshTokenContext.getRefreshToken());
			RequestDispatcher dispatcher = request.getRequestDispatcher(refreshTokenContext.getUri());
			refreshTokenContext.setUri(null);
			dispatcher.forward(request, response);
		}

	}
}

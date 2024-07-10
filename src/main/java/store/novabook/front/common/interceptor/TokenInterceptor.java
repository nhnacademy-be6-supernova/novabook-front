package store.novabook.front.common.interceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.api.member.member.service.TestService;
import store.novabook.front.common.security.RefreshTokenContext;
import store.novabook.front.common.security.exception.NotLoginException;

@Slf4j
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

	private final RefreshTokenContext refreshTokenContext;
	private final MemberService memberService;

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

		String requestURI = request.getRequestURI();
		String uri = refreshTokenContext.getUri();
		if (!request.getRequestURI().equals("/error")
			&& !request.getRequestURI().equals("/admin")) {
			refreshTokenContext.setUri(request.getRequestURI());
		}
		return true;
	}

	// @Override
	// public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	// 	ModelAndView modelAndView) throws Exception {
	// 	System.out.println();
	//
	// 	// if (refreshTokenContext.getTokenData() == null || request.getRequestURI().equals("/error")) {
	// 	// 	return;
	// 	// }
	// 	// if (refreshTokenContext.getTokenData().equals("expired")) {
	// 	// 	refreshTokenContext.setTokenData(null);
	// 	// 	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
	// 	// 	return;
	// 	// }
	// 	//
	// 	// Cookie cookie = new Cookie("Authorization", refreshTokenContext.getTokenData());
	// 	// cookie.setPath("/");
	// 	// // cookie.setDomain("novabook");
	// 	// cookie.setMaxAge(60 * 60 * 24 * 7);
	// 	// response.addCookie(cookie);
	// 	// response.setHeader("access", refreshTokenContext.getTokenData());
	// 	// response.setHeader("refresh", refreshTokenContext.getRefreshToken());
	// 	// RequestDispatcher dispatcher = request.getRequestDispatcher(refreshTokenContext.getUri());
	// 	// refreshTokenContext.setUri(null);
	// 	// dispatcher.forward(request, response);
	//
	// }
	//
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {
		// 예외가 터지면 afterCompletion이 실행됨

		// if (ex == null || !ex.getMessage().contains("303 See Other")) {
		// 	RequestDispatcher dispatcher2 = request.getRequestDispatcher(refreshTokenContext.getUri());
		// 	dispatcher2.forward(request, response);
		// 	refreshTokenContext.setUri(null);
		// 	return;
		// }
		//
		// Cookie[] cookies = request.getCookies();
		// String refreshToken = "";
		// for (Cookie cookie : cookies) {
		// 	if ("Refresh".equals(cookie.getName())) {
		// 		refreshToken = cookie.getValue();
		// 	}
		// }
		//
		// if (refreshTokenContext.getUri() == null) {
		// 	return;
		// }
		//
		// GetNewTokenRequest getNewTokenRequest = new GetNewTokenRequest(refreshToken);
		// GetNewTokenResponse getNewTokenResponse = memberService.newToken(getNewTokenRequest);
		//
		// response.setHeader("access", getNewTokenResponse.accessToken());
		// response.setHeader("refresh", refreshToken);
		//
		// Cookie accessCookie = new Cookie("Authorization", getNewTokenResponse.accessToken());
		// accessCookie.setPath("/");
		// accessCookie.setMaxAge(60 * 60 * 24 * 7);
		// response.addCookie(accessCookie);
		//
		// RequestDispatcher dispatcher = request.getRequestDispatcher(refreshTokenContext.getUri());
		// // if (!request.getRequestURI().equals("/error")) {
		// // 	String uri = refreshTokenContext.getUri();
		// // 	refreshTokenContext.setUri(null);
		// // }
		// try {
		// 	dispatcher.forward(request, response);
		// } catch (ServletException e) {
		// 	throw new RuntimeException(e);
		// } catch (IOException e) {
		// 	throw new RuntimeException(e);
		// }

		if (ex instanceof feign.RetryableException) {

			Cookie[] cookies = request.getCookies();
			String refreshToken = "";
			for (Cookie cookie : cookies) {
				if ("Refresh".equals(cookie.getName())) {
					refreshToken = cookie.getValue();
					break;
				}
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(refreshTokenContext.getUri());
			GetNewTokenRequest getNewTokenRequest = new GetNewTokenRequest(refreshToken);
			GetNewTokenResponse getNewTokenResponse = memberService.newToken(getNewTokenRequest);
			response.setHeader("access", getNewTokenResponse.accessToken());
			response.setHeader("refresh", refreshToken);
			Cookie cookie = new Cookie("Authorization", refreshTokenContext.getTokenData());
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 7);
			response.addCookie(cookie);
			if (!request.getRequestURI().equals("/error")) {
				String uri = refreshTokenContext.getUri();
				refreshTokenContext.setUri(null);
			}
			dispatcher.forward(request, response);
			return;
		}

		if(refreshTokenContext.getUri() != null) {

			RequestDispatcher dispatcher2 = request.getRequestDispatcher(refreshTokenContext.getUri());
			dispatcher2.forward(request, response);
			refreshTokenContext.setUri(null);
			return;
		}

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
			if (!request.getRequestURI().equals("/error")) {
				String uri = refreshTokenContext.getUri();
				refreshTokenContext.setUri(null);
			}
			dispatcher.forward(request, response);
		}

	}
}

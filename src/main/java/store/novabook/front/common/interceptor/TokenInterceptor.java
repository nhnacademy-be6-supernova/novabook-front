package store.novabook.front.common.interceptor;

import java.io.IOException;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.security.RefreshTokenContext;

@Slf4j
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

	private final RefreshTokenContext refreshTokenContext;
	private final MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		if (!request.getRequestURI().equals("/error")
			&& !request.getRequestURI().equals("/admin")) {
			refreshTokenContext.setUri(request.getRequestURI());
		}
		return true;
	}


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {

		if (ex == null || !ex.getMessage().contains("303 See Other")) {
			// RequestDispatcher dispatcher2 = request.getRequestDispatcher(refreshTokenContext.getUri());
			// dispatcher2.forward(request, response);
			// refreshTokenContext.setUri(null);
			return;
		}

		Cookie[] cookies = request.getCookies();
		String refreshToken = "";
		for (Cookie cookie : cookies) {
			if ("Refresh".equals(cookie.getName())) {
				refreshToken = cookie.getValue();
			}
		}

		if (refreshTokenContext.getUri() == null) {
			return;
		}

		GetNewTokenRequest getNewTokenRequest = new GetNewTokenRequest(refreshToken);
		GetNewTokenResponse getNewTokenResponse = memberService.newToken(getNewTokenRequest);

		response.setHeader("access", getNewTokenResponse.accessToken());
		response.setHeader("refresh", refreshToken);

		Cookie accessCookie = new Cookie("Authorization", getNewTokenResponse.accessToken());
		accessCookie.setPath("/");
		accessCookie.setMaxAge(60 * 60 * 24 * 7);
		response.addCookie(accessCookie);

		RequestDispatcher dispatcher = request.getRequestDispatcher(refreshTokenContext.getUri());
		// if (!request.getRequestURI().equals("/error")) {
		// 	String uri = refreshTokenContext.getUri();
		// 	refreshTokenContext.setUri(null);
		// }
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}

package store.novabook.front.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.dto.request.IsExpireAccessTokenRequest;
import store.novabook.front.api.member.member.dto.response.IsExpireAccessTokenResponse;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.UnauthorizedException;
import store.novabook.front.common.util.LoginCookieUtil;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

	private final MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		boolean isLoggedIn = isLoggedIn(request, response);
		request.setAttribute("isLoggedIn", isLoggedIn);
		return true;
	}

	private boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return true;
		}
		String refreshToken = "";
		String accessToken = "";
		for (Cookie cookie : cookies) {
			if ("Refresh".equals(cookie.getName())) {
				refreshToken = cookie.getValue();
			} else if ("Authorization".equals(cookie.getName())) {
				accessToken = cookie.getValue();
			}
		}
		if (refreshToken.isEmpty() && accessToken.isEmpty()) {
			return false;
		}

		if (accessToken.isEmpty()) {
			GetNewTokenResponse getNewTokenResponse = memberService.newToken(new GetNewTokenRequest(refreshToken));
			log.info("새로운 토큰 발급 {}", getNewTokenResponse);

			if (getNewTokenResponse.accessToken().equals("expired")) {
				log.info("리프레시 토큰 만료");
				LoginCookieUtil.deleteAuthorizationCookie(response);
				throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
			}

			LoginCookieUtil.createAccessTokenCookie(response, getNewTokenResponse.accessToken());

			request.setAttribute("reissuedAccessToken", getNewTokenResponse.accessToken());
			return true;
		}


		IsExpireAccessTokenResponse isExpireAccessTokenResponse = memberService.isExpireAccessToken(
			new IsExpireAccessTokenRequest(accessToken));

		if (isExpireAccessTokenResponse.isExpire()) {
			GetNewTokenResponse getNewTokenResponse = memberService.newToken(new GetNewTokenRequest(refreshToken));
			log.info("새로운 토큰 발급 {}", getNewTokenResponse);

			if (getNewTokenResponse.accessToken().equals("expired")) {
				log.info("리프레시 토큰 만료");
				LoginCookieUtil.deleteAuthorizationCookie(response);
				throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
			}

			LoginCookieUtil.createAccessTokenCookie(response, getNewTokenResponse.accessToken());

			request.setAttribute("reissuedAccessToken", getNewTokenResponse.accessToken());
		}

		return true;
	}
}

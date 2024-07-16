package store.novabook.front.common.interceptor;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.dto.GetNewTokenRequest;
import store.novabook.front.api.member.member.dto.GetNewTokenResponse;
import store.novabook.front.api.member.member.dto.request.IsExpireAccessTokenRequest;
import store.novabook.front.api.member.member.dto.response.IsExpireAccessTokenResponse;
import store.novabook.front.api.member.member.service.MemberService;
import store.novabook.front.common.util.JwtDecoderUtil;

@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

	private final MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response,
		@NotNull Object handler) throws
		Exception {

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
		if (refreshToken.isEmpty() || accessToken.isEmpty()) {
			return true;
		}

		IsExpireAccessTokenResponse isExpireAccessTokenResponse = memberService.isExpireAccessToken(
			new IsExpireAccessTokenRequest(accessToken));

		if (isExpireAccessTokenResponse.isExpire()) {
			GetNewTokenResponse getNewTokenResponse = memberService.newToken(new GetNewTokenRequest(refreshToken));

			Cookie accessCookie = new Cookie("Authorization", getNewTokenResponse.accessToken());
			accessCookie.setPath("/");
			accessCookie.setMaxAge(60 * 60 * 24 * 7);
			accessCookie.setHttpOnly(true);
			response.addCookie(accessCookie);

			request.setAttribute("reissuedAccessToken", getNewTokenResponse.accessToken());
		}

		return true;
	}
}

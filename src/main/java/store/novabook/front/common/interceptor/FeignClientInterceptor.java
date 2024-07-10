package store.novabook.front.common.interceptor;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.novabook.front.common.security.RefreshTokenContext;

@Component
@RequiredArgsConstructor
@Slf4j
public class FeignClientInterceptor implements RequestInterceptor {

	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final RefreshTokenContext refreshTokenContext;

	private static final String AUTHORIZATION = "Authorization";
	private static final String REFRESH = "Refresh";
	private static final String BEARER = "Bearer";

	@Override
	public void apply(RequestTemplate template) {
		log.error("out :   {}, {}", template.feignTarget().url(), template.method());
		if (template.feignTarget().url().equals("http://localhost:9777/api/v1/store/categories") && template.method()
			.equals("GET")) {
			log.error("in :   {}, {}", template.feignTarget().url(), template.method());
			return;
		}
		if (template.feignTarget().name().contains("payco")) {
			return;
		}
		//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1dWlkIjoiNDdiM2NjMWItOWI1Mi00ODJkLThjY2UtOWY5Mzk5OWE5ODZjIiwiYXV0aG9yaXRpZXMiOiJST0xFX01FTUJFUlMiLCJjYXRlZ29yeSI6InJlZnJlc2giLCJpYXQiOjE3MjA1MTA0OTUsImV4cCI6MTcyMDUxNjQ5NX0.z-vAh-mC5T2X3HaLtYzSp7XwiJWi3fPOqJ_GeXgoFK8
		//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1dWlkIjoiNTE3NDAzNDQtMzg0Yi00ZDg3LTkwYWMtOTM1YjcyYmNkY2JlIiwiYXV0aG9yaXRpZXMiOiJST0xFX01FTUJFUlMiLCJjYXRlZ29yeSI6InJlZnJlc2giLCJpYXQiOjE3MjA1OTUyNDMsImV4cCI6MTcyMDYwMTI0M30.0K4Zrnux9rFB_rTmchT2d0fyPDSjl64vqOQKpHRKRCk
		Cookie[] cookies = request.getCookies();
		String access = response.getHeader("access");
		String refresh = response.getHeader("refresh");
		if (Objects.nonNull(access)) {
			template.header(AUTHORIZATION, BEARER + " " + access);
			template.header(REFRESH, BEARER + " " + refresh);
			refreshTokenContext.setTokenData(null);
			refreshTokenContext.setRefreshToken(null);
			// refreshTokenContext.setUri(null);
			return;
		}
		if (Objects.isNull(cookies)) {
			return;
		}
		Arrays.stream(cookies)
			.forEach(cookie -> {
				if (AUTHORIZATION.equals(cookie.getName())) {
					template.header(AUTHORIZATION, BEARER + " " + cookie.getValue());
				} else if (REFRESH.equals(cookie.getName())) {
					template.header(REFRESH, BEARER + " " + cookie.getValue());
				}
			});
	}
}
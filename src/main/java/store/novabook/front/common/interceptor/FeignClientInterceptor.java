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
		if (template.feignTarget().url().equals("http://localhost:9777/api/v1/store/categories") && template.method()
			.equals("GET")) {
			return;
		}
		if (template.feignTarget().name().contains("payco")) {
			return;
		}


		Cookie[] cookies = request.getCookies();
		String access = response.getHeader("access");
		String refresh = response.getHeader("refresh");
		if (Objects.nonNull(access)) {
			template.header(AUTHORIZATION, BEARER + " " + access);
			template.header(REFRESH, BEARER + " " + refresh);
			refreshTokenContext.setTokenData(null);
			refreshTokenContext.setRefreshToken(null);
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
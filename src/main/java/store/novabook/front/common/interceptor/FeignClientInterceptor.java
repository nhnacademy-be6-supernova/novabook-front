package store.novabook.front.common.interceptor;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class FeignClientInterceptor implements RequestInterceptor {

	private final HttpServletRequest request;

	private static final String AUTHORIZATION = "Authorization";
	private static final String REFRESH = "Refresh";
	private static final String BEARER = "Bearer";

	@Override
	public void apply(RequestTemplate template) {
		if (template.feignTarget().name().contains("payco")) {
			return;
		}

		String reissuedAccessToken = (String)request.getAttribute("reissuedAccessToken");
		Cookie[] cookies = request.getCookies();
		if (Objects.isNull(cookies)) {
			return;
		}
		Arrays.stream(cookies)
			.forEach(cookie -> {
				if (AUTHORIZATION.equals(cookie.getName())) {
					if (reissuedAccessToken != null) {
						template.header(AUTHORIZATION, BEARER + " " + reissuedAccessToken);
					} else {
						template.header(AUTHORIZATION, BEARER + " " + cookie.getValue());
					}
				} else if (REFRESH.equals(cookie.getName())) {
					template.header(REFRESH, BEARER + " " + cookie.getValue());
				}
			});
	}
}
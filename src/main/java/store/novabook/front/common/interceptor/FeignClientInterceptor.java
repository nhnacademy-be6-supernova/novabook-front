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

@Component
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

	private final HttpServletRequest request;
	private final HttpServletResponse response;

	@Override
	public void apply(RequestTemplate template) {

		Cookie[] cookies = request.getCookies();
		String access = response.getHeader("access");
		String refresh = response.getHeader("refresh");
		if (Objects.isNull(access)) {
			if (Objects.nonNull(cookies)) {
				Arrays.stream(cookies)
					.forEach(cookie -> {
						if ("Authorization".equals(cookie.getName())) {
							if (Objects.isNull(access)) {
								template.header("Authorization", "Bearer " + cookie.getValue());
							} else {
								template.header("Authorization", "Bearer " + access);
							}
						} else if ("Refresh".equals(cookie.getName())) {
							template.header("Refresh", "Bearer " + cookie.getValue());
						}
					});
			}
		} else {
			template.header("Authorization", "Bearer " + access);
			template.header("Refresh", "Bearer " + refresh);
		}

	}
}

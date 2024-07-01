package store.novabook.front.common.config;

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

	// @Autowired
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final RefreshTokenContext refreshTokenContext;

	@Override
	public void apply(RequestTemplate template) {

		Cookie[] cookies = request.getCookies();
		String header = response.getHeader("refresh");
		if (Objects.isNull(cookies)) {
			if (Objects.isNull(header)) {
				return;
			} else {
				template.header("Authorization", "Bearer " + header);
			}
		} else {
			Arrays.stream(cookies)
				.forEach(cookie -> {
					if ("Authorization".equals(cookie.getName())) {
						if (Objects.isNull(header)) {
							template.header("Authorization", "Bearer " + cookie.getValue());
						} else {
							template.header("Authorization", "Bearer " + header);
						}
					} else if ("Refresh".equals(cookie.getName())) {
						template.header("Refresh", "Bearer " + cookie.getValue());
					}
				});
		}

	}
}

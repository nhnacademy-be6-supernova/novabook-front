package store.novabook.front.common.config;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

	// @Autowired
	private final HttpServletRequest request;
	private final GlobalContext globalContext;

	@Override
	public void apply(RequestTemplate template) {

		Cookie[] cookies = request.getCookies();
		if (Objects.isNull(cookies)) {
			return;
		}

		Arrays.stream(cookies)
			.forEach(cookie -> {
				if ("Authorization".equals(cookie.getName())) {
					template.header("Authorization", "Bearer " + cookie.getValue());
					if (Objects.nonNull(globalContext.getSomeData())) {
						template.header("Authorization", globalContext.getSomeData());
					}
				} else if ("Refresh".equals(cookie.getName())) {
					template.header("Refresh", "Bearer " + cookie.getValue());
				}
			});
	}
}

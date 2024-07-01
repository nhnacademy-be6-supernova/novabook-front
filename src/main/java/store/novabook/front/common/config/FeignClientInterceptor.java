package store.novabook.front.common.config;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

	// @Autowired
	private final HttpServletRequest request;

	@Override
	public void apply(RequestTemplate template) {
		if (request.getCookies() != null) {
			Arrays.stream(request.getCookies())
				.forEach(cookie -> {
					if ("Authorization".equals(cookie.getName())) {
						template.header("Authorization", "Bearer " + cookie.getValue());
					} else if ("Refresh".equals(cookie.getName())) {
						template.header("Refresh", "Bearer " + cookie.getValue());
					}
				});
		}
	}
}
